<template>
	<div>
		<v-combobox
				v-model="model"
				:filter="filter"
				:hide-no-data="!search"
				:items="items"
				:search-input.sync="search"
				hide-selected
				flat
				outlined
				dense
				multiple
				small-chips
				hint="태그는 최대 5개까지 가능합니다"
				style="border-radius:0px; width: 100%"
			>
			<template v-slot:no-data>
				<v-list-item>
					<!-- <span class="subheading">Create</span> -->
					<v-chip
						:color="`${color} lighten-3`"
						small
					>
						{{ search }}
					</v-chip>
				</v-list-item>
			</template>
			<template v-slot:selection="{ attrs, item, parent, selected }">
				<v-chip
					v-if="item === Object(item)"
					v-bind="attrs"
					:color="`${item.color} lighten-3`"
					:input-value="selected"
					small
				>
					<span class="pr-2">
						{{ item.text }}
					</span>
					<v-icon
						x-small
						@click="parent.selectItem(item)"
					>mdi-close</v-icon>
				</v-chip>
			</template>
			<template v-slot:item="{ index, item }">
				<v-text-field
					v-if="editing === item"
					v-model="editing.text"
					autofocus
					flat
					background-color="transparent"
					hide-details
					solo
					@keyup.enter="edit(index, item)"
				></v-text-field>
				<v-chip
					v-else
					:color="`${item.color} lighten-3`"
					dark
					small
				>
					{{ item.text }}
				</v-chip>
				<v-spacer></v-spacer>
				<v-list-item-action @click.stop>
					<v-btn
						icon
						@click.stop.prevent="edit(index, item)"
					>
						<v-icon>{{ editing !== item ? 'mdi-pencil' : 'mdi-check' }}</v-icon>
					</v-btn>
				</v-list-item-action>
			</template>
		</v-combobox>
		<div class="sub_btn">
		<v-btn 
			@click="onSubmitButton"
			color="#08d3bc"
			outlined
			tile
		>출간하기</v-btn>
		<v-btn
			class="ml-2"
			@click="onSaveButton"
			color="#5c7bf4"
			outlined
			tile
		>임시저장</v-btn>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
	name:'TagInputBox',
	data() {
		return {
			activator: null,
			attach: null,
			// colors: ['green', 'purple', 'indigo', 'cyan', 'teal', 'orange'],
			color: "#08d3bc",
			editing: null,
			index: -1,
			items: [
				{ header: '엔터를 누르면 태그가 입력됩니다.' },
			],
			nonce: 0,
			menu: false,
			model: [],
			x: 0,
			search: null,
			y: 0,
		}
	},
	created() {
		// tag
		axios.post(API_URL+'api/v2/p/',
			{
				"pid" : this.$attrs.pid,
				"uid" :	this.$attrs.uid
			})
			.then(response => {
				response.data.tag.forEach(el => {
					this.model.push(
					{"color": "#08d3bc",
						"text" : el}
				);
				})
				
			})

	},
	watch: {
		// 태그 글자수 20자 미만으로 제한 두기
		// 태그 빈칸은 안받기, 빈칸 넣어두면 없애기
		model (val, prev) {
			if (prev.length > 5) {
				return this.$nextTick(() => this.model.pop())
			}
			if (val.length === prev.length) return
				this.model = val.map(v => {
					if (typeof v === 'string') {
						v = {
							text: v,
							color: this.color,
							}
						this.items.push(v)
						// this.nonce++
						}
					return v
			})
		},
	},

	methods: {
		edit (index, item) {
			if (!this.editing) {
				this.editing = item
				this.index = index
			} else {
				this.editing = null
				this.index = -1
			}
		},
		filter (item, queryText, itemText) {
			if (item.header) return false

			const hasValue = val => val != null ? val : ''
			const text = hasValue(itemText)
			const query = hasValue(queryText)

			return text.toString()
			.toLowerCase()
			.indexOf(query.toString().toLowerCase()) > -1
		},
		onSubmitButton() {
			this.$emit('submit-post', this.model)
		},
		onSaveButton() {
			this.$emit('save-post', this.model)
		}
	},
}
</script>

<style>

.sub_btn{
	float: right;
	margin-top: -15px;
}
</style>