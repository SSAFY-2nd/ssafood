<template>
  <div>
    <div class="main_comment">
		<div>
			<h5>{{child.author}}</h5>
			<div class="mc_content">
				<p>{{ child.content }}</p>
			</div>
		</div>
		<div class="btngrp">
			<span
				v-if="child.isauthor"
				style="margin-right: 10px;"
				@click="onCommentDelete(child)"
				>삭제</span>
			<span 
				v-if="child.isauthor"
				style="margin-right: 10px;"
				@click="ClickChildUpdateBtn(child)"
				>수정</span>
		</div>
	</div>
    <div>
      <div v-if="ChildUpdateFlag" class="update-box">
				<v-text-field
					v-model="ChildUpdateInput"
					solo
					rows="2"
					clearable
				></v-text-field>
				<v-btn @click="onChildUpdate(child)" class="update-btn">수정하기</v-btn>
			</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const API_URL = 'http://localhost:8081/'
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const storage = window.sessionStorage

export default {
  name: 'ChildComment',
  data() {
    return {
      child: this.$attrs.child,
      ChildCommentInput: '',
			ChildUpdateInput: '',
			ChildUpdateFlag: false,
    }
  },
  mounted() {
  },
  methods: {
    // *** Child Comment ***
		// 대댓글 수정 버튼 - ChildUpdateFlag 토글
		ClickChildUpdateBtn(one_child) {
      this.ChildUpdateInput = one_child.content
			this.ChildUpdateFlag = !this.ChildUpdateFlag
		},
		onChildUpdate(one_child) {
			if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
				const tmp_comment = {
					content: this.ChildUpdateInput,
					cid: one_child.cid
				}
				axios.put(API_URL + `api/v3/`, tmp_comment)
					.then(() => {
						alert('수정되었습니다.')
						this.$emit('Click-Child-Update-Btn')
						this.ChildUpdateInput = ''
						this.ChildUpdateFlag = !this.ChildUpdateFlag
					})
			} else {
				alert('비정상적인 접근입니다')
			}
    },
    onCommentDelete(one_child) {
			if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
				axios.delete(API_URL + `api/v3/${one_child.cid}`)
					.then(() => this.$emit('Click-Child-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
  }
}
</script>

<style scoped>
.main_comment {
	width: 100%;
	display: flex;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
	margin : 10px 0 10px 0;
}
.main_comment div h5{
	font-weight: 800;
	margin-right: 20px;
}
.mc_content {
	max-height: 3.6rem;
	overflow: hidden;
	text-overflow: ellipsis;
}
.mc_content p{
	font-size: 1rem;
	line-height: 1.2rem;
}
.btngrp{
	margin-top: -2px;
}
.btngrp span {
	cursor: pointer;
	font-size: 14px;
}
</style>