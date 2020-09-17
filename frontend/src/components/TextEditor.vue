<template>
	<div>
		<Navbar></Navbar>
		<div class="inner">
				<v-text-field
					dense
					flat
					solo
					hide-details
					v-model="title"
					label="글 제목을 입력해주세요"
					required
					style="border-radius:0px; border: 1px solid #e0e5ee"
					class="mb-5"
				></v-text-field>
				<editor :initialValue="this.tempText" ref="toastuiEditor" height="500px"/>
				<div class="posting">
					<div class="thumbnail">
						<div class="regthumb">
							<h4 class="cate_desc">썸네일 이미지</h4>
								<!-- 이미지 업로드 (모달) -->
							<div class="img_btn">
								<v-btn 
									text
									depressed 
									outlined
									width="150px" 
									color="#6e8af8"
									style="border: 1px solid #6e8af8"
								>
									<label for="file-input" style="padding-top:9px">이미지 업로드</label></v-btn>
								<input type="file" accept="image/*" @change="uploadthumbImage($event)" id="file-input" class="uploadimg">
							</div>
						</div>
						<div class="profile_area">
							<div class="imgbox" v-if="thumbImage">
								<!-- 회원 프로필 이미지 디비에서 조회 -->
								<!-- [추가]이미지 변화에 대한 처리 -->
								<img v-bind:src="thumbImage">
							</div>
						</div>
					</div>
					<div class="tagreg">
						<h4 class="cate_desc">태그 입력칸</h4>        
						<TagInputBox 
							:pid ="TagInputBoxPID"
							:uid ="TagInputBoxUID"
							@submit-post="createAction"
							@save-post="saveAction"
						/>
					</div>
				</div>
		</div>
	</div>
</template>
<script>
import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import { Editor } from '@toast-ui/vue-editor'
import axios from 'axios'
import TagInputBox from './TagInputBox.vue'
import Navbar from '../components/Navbar.vue'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const FLICKR_URL = 'https://up.flickr.com/services/upload/'

export default {
	name: 'TextEditor',
	components: {
		Editor,
		TagInputBox,
		Navbar,
	},
	data(){
		return {
			editorText: '',
			title: '',
			thumbImage: '',
			uid: '',
			tempText: '',
			taglist: [],
			TagInputBoxPID : '',
			TagInputBoxUID : ''
		}
	},
	created(){
		if(this.$route.params.data.title != null){
			this.title = this.$route.params.data.title
			this.tempText = this.$route.params.data.content
			if(this.$route.params.data.thumbnail != null){
				this.thumbImage = this.$route.params.data.thumbnail
			}
		}
		
		if(this.$route.params.data.pid != null){
			this.TagInputBoxPID = this.$route.params.data.pid
		}
		if(this.$route.params.data.uid != null){
			this.TagInputBoxPID = this.$route.params.data.uid
		}
	},
	methods: {

		createAction(tagData) {
			// 태그 데이터 정리해서 보내기
			let tagList = new Array()
			
			tagData.forEach(tag => {
				tagList.push(tag["text"])
			})
			var textdata = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
			this.editorText = textdata

			axios.post(API_URL+'api/v2/', {
				author: storage.getItem("login_user"),
				title: this.title,
				content: this.editorText,
				tag: tagList,
				uid: `${storage.getItem("uid")}`,
				thumbnail: storage.getItem("thumbImage")
			})
			.then(() => {
				storage.removeItem("thumbImage")
				this.$router.push('/')
			})
			.catch(err => console.log(err))
		},
		saveAction(tagData) {

			//	 태그 데이터 정리해서 보내기
			let tagList = new Array()
			tagData.forEach(tag => {
				tagList.push(tag["text"])
			})
			var textdata = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
			this.editorText = textdata
			axios.put(API_URL+"api/v2/temp",{
				pid : `${storage.getItem("pid")}`,
				uid : `${storage.getItem("uid")}`,
				author : `${storage.getItem("login_user")}`,
				title: this.title,
				content: this.editorText,
				tag: tagList,
				thumbnail: storage.getItem("thumbImage")
			})
			.then(() => {
				storage.removeItem("thumbImage")
				this.$router.push('/')
			})
			.catch(err => console.log(err))
		},

		// 업로드 이미지
		uploadthumbImage(e){

			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			// 미리보기용
			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.thumbImage = e.target.result; // profileImage 설정
			};

			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v2/img'+`?title=${this.title}&uid=${storage.getItem("uid")}`,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
				)
				.then(response => {
					this.thumbImage = response.data;
					storage.setItem("thumbImage", response.data)
				})
				.catch((err) => {
					console.log("프로필 이미지 업로드 실패")
					console.log(err);
				})
		},
	},
}
</script>

<style scoped>
h3 {
	margin-top: 20px;
	margin-bottom: 5px;
}
button {
	border-radius: 0px;
}
.thumbnail {
	width:48%;
	margin-right: 25px;
}
.tagreg {
	width:50%;
	float: right;
	margin:7px 0 20px;
}
.uploadimg {
		display:none;
	}

.img_btn{
	font-family: 'NanumSquare', Noto Sans Light, sans-serif;
	width:150px;
	margin-top:-6px;
	float: right;
}
.imgbox {
		border-radius: 5px;
		overflow: hidden;
		margin: 0 auto 40px auto;
		border: 1px solid #e0e5ee;
	}
.imgbox> img {
	width: 100%;
	height: 100%;
}
.cate_desc{
		font-family: 'NanumSquare', Noto Sans Light, sans-serif;
		font-weight: 600;
		font-size: 1rem;
		margin-right: 20px;
	}
.posting{
	display: flex;
	margin-top:20px;
}
.regthumb{
	display:flex;
	margin:7px 0 20px;
}
</style>