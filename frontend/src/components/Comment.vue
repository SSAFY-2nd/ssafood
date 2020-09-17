<template>
	<!-- 댓글(comment)+대댓글(comment.child) -->
	<div class="comment-body">
		<!-- 댓글 하나 -->
		<div class="comment-box">
			<!-- 댓글 작성자, 내용 -->
			<div class="main_comment">
				<div>
					<h5>{{ comment.author }}</h5>
					<div class="mc_content">
					<p>{{ comment.content }}</p>
					</div>
				</div>
				<div class="btngrp">
					<span style="margin-right: 10px;"
						@click="onCommentDelete(comment)"
					>삭제</span>
					<span
						@click="ClickUpdateBtn(comment)"
					>수정</span>
				</div>
			</div>
			<!-- 댓글 삭제, 수정 버튼 -->
			
		</div>
		<!-- 댓글 수정 버튼 누를 시 인풋 -->
		<div v-if="UpdateFlag" class="update-box">
			<v-text-field
				v-model="CommentUpdateInput"
				rows="2"
				clearable
				style="border-radius:0px"
				flat
				outlined
			></v-text-field>
			<v-btn @click="onCommentUpdate(comment)" class="update-btn">수정하기</v-btn>
		</div>
		<!-- 대댓글 버튼 & 갯수 -->
		<div class="child-comment-btn">
			<!-- 대댓글 보이기/숨기기 버튼 -->
			<v-btn @click="ClickChildBtn" depressed outlined fab x-small dark color="#08d3bc"
				style="border-radius:0px; margin-right: 10px">
				<v-icon v-if="!ChildFlag" dark>mdi-plus</v-icon>
				<v-icon v-if="ChildFlag">mdi-minus</v-icon>
			</v-btn>
			<!-- 대댓글 갯수 따라서 다른 설명 -->
			<div class="comment_c">
				<p class="">{{ comment.child.length }}개의 코멘트</p>
			</div>
		</div>
		<!-- 대댓글 -->
		<div v-if="ChildFlag">
			<!-- 대댓글 리스트 -->
			<div v-for="child in comment.child" :key="child.cid" class="child-comment">
				<ChildComment
					:child="child"
					@Click-Child-Update-Btn="ChildChange"
					@Click-Child-Delete-Btn="ChildChange"
				/>
			</div>
			<!-- 대댓글 작성 인풋 -->
			<div class="child-box">
				<span>댓글 코멘트</span>
				<v-text-field
					hide-details
					v-model="ChildCommentInput"
					flat
					outlined
					clearable
					style="border-radius:0px"
					rows="2"
				></v-text-field>
				<div class="comment_btn">
					<v-btn 
						color="#08d3bc"
						depressed
						style="border-radius:0px"
						class="white--text" @click="onChildCommentCreate(comment.cid)">작성</v-btn>
				</div>
			</div>
			
		</div>
	</div>
</template>

<script>
import axios from 'axios'
import ChildComment from './ChildComment.vue'

const API_URL = 'http://localhost:8081/'
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const storage = window.sessionStorage

export default {
	name: 'Comment',
	components: {
		ChildComment,
	},
	data() {
		return {
			comment: this.$attrs.comment,
			CommentUpdateInput: '',
			UpdateFlag: false,
			ChildFlag: false,
		}
	},
	created() {
	},
	methods: {
		// *** Parent Comment ***
		// 댓글 Delete 메서드
		onCommentDelete(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				axios.delete(API_URL + `api/v3/${one_comment.cid}`)
					.then(() => this.$emit('Click-Delete-Btn'))
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// 댓글 Update 메서드
		onCommentUpdate(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				const tmp_comment = {
					content: this.CommentUpdateInput,
					cid: one_comment.cid
				}
				axios.put(API_URL + `api/v3/`, tmp_comment)
					.then(() => {
						alert('수정되었습니다.')
						this.$emit('Click-Update-Btn')
						this.CommentUpdateInput = ''
						this.UpdateFlag = !this.UpdateFlag
					})
			} else {
				alert('비정상적인 접근입니다')
			}
		},
		// Comment에 Child가 있는지 없는지 체크
		isCommentChild(one_comment_child) {
			if (one_comment_child.length) {
				// one_comment_child.forEach(element => {
				// 	this.isCommentauthor(element)
				// })
				return true
			}
			return false
		},
		ClickUpdateBtn(one_comment) {
			if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
				this.CommentUpdateInput = one_comment.content
				this.UpdateFlag = !this.UpdateFlag
			}
		},
		

		// *** Child Comment ***
		// 대댓글 Create 메서드
		onChildCommentCreate(pcid) {
			if (storage.getItem("login_user")) {
				const tmp_comment = {
					author: storage.getItem("login_user"),
					content: this.ChildCommentInput,
					uid: storage.getItem("uid"),
					pid: storage.getItem("pid"),
					parent: pcid
				}
				axios.post(API_URL + 'api/v3/r', tmp_comment)
					.then(() => this.$emit('Child-Create'))
					.catch(err => console.log(err))
			}
		},
		// 대댓글 보기, 숨기기 버튼 - ChildFlag 토글 메서드
		ClickChildBtn() {
			this.ChildFlag = !this.ChildFlag
			this.$nextTick()
		},
		// ChildComment의 이벤트 발생 듣고 PostDetail로 올림
		ChildChange() {
			this.$emit("Click-Child-Change-Btn")
		}
	}
}
</script>

<style scoped>
.comment-body {
	margin: 10px 0;
	padding: 0 0 0.5rem 0;
}
.child-box {
	display: inline;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;

}
.child-comment-btn {
	display: flex;
}
.update-box {
	display: flex;
}
.update-btn {
	margin-left: 5px;
}
.Child-Create-Btn {
	margin-bottom: 2rem;
}
.child-comment {
	display: flex;
	margin: 0 0 0.5rem 2rem;
}
.btngrp{
	margin-top: -2px;
}
.btngrp span {
	cursor: pointer;
	font-size: 14px;
}
.main_comment {
	width: 100%;
	display: flex;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
}
.main_comment h5{
	font-weight: 800;
}
h5 {
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
.comment_c p{
	padding-top:7px;
	font-size: 14px;
}
.comment_btn {
  display:flex;
}
.comment_btn button {
	margin-left: auto;
	margin-top : 10px;
}
</style>