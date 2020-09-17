<template>
  <div>
		<header>
			<Navbar></Navbar>
			<div class="search">
				<div class="inner_box">
					<h2>검색</h2>
					<p class="do_txt">다른 Do!블로그가 궁금하다면?</p>
						<v-select
							v-model="select"
							:items="options"
							item-text="name"
							item-value="abbr"
							menu-props="auto"
							hide-details
							dense
							label="Solo field"
							solo
							flat
							style="display:inline-block; width:20%; margin: 0 5px 0 0;"
							return-object
						></v-select>
						<v-text-field
							v-model="kword"
							hide-details
							dense
							flat
							solo
							autofocus
							style="display:inline-block; width:79%"
							@keyup.enter="postList()"
						>
							<template v-slot:append>
								<v-btn depressed text color="#5c7bf4" 
									:disabled="kword.length<1 ? true:false" 
									@click="postList()"
									><v-icon>fas fa-search</v-icon></v-btn>
							</template>
						</v-text-field>
				</div>
				
			</div>
		</header>
		<div class="content_list">
			<div class="postcount" v-if="postcnt>-1">
				<!-- kword에 밑줄 어떻게 긋지? -->
				<h3><span>{{kword}}</span>에 대한 검색 결과는 <strong>{{postcnt}}</strong>개입니다.</h3>
			</div>
			<div v-if="posts.length>0">
				<div v-for="post in posts" :key="post.pid" class="post_list" @click="postdetail(post)">
					<div class="profile">
						<!-- 작성자 프로필 이미지. 클릭 시 개인 블로그로 이동-->
						<a><img :src="profileImg"></a>
						<div class="nickname">
							{{post.author}}
						</div>
					</div>
					<!-- 기본섬네일로 지정되어 있으면 검색 시 표시 안 함 -->
					<div class="post_thumbnail" v-if="post.thumbnail!=basicthumb">
						<img :src="post.thumbnail">
					</div>
					<div class="post_info">
						<h2>{{post.title}}</h2>
						<p class="post_content">{{post.content}}</p>
						{{post.publishedTime}}
					</div>
				</div>
			</div>
		</div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
// import Post from '../models/post.js'

import axios from 'axios'

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
	name: 'Introduce',
	components: {
		Navbar,
	},
	data() {
		return{
		select: { name: '키워드', abbr: 't'},
		options: [{name: '태그', abbr: 'a'}
						, {name: '작성자', abbr: 'u'}
						, {name: '키워드', abbr: 't'},],
		kword:'',
		posts : {
			pid: '',
			author: '',
			title: '',
			content: '',
			publishedTime: '',
			thumbnail:'',
		},
		postcnt:-1,
		date: '',
		profileImg: '',
		basicthumb: 'http://i3a507.p.ssafy.io/img/default/thumbnail.jpg'
		}
	},
	created(){
		this.profileImg = storage.profileImage;
	},
	methods: {
		postList(){
			axios
				.get(API_URL+`api/v2/find/${this.select.abbr}/${this.kword}`)
				.then(({data})=>{
					this.postcnt = data.length;
					data.forEach(el => {
						let year = el.publishedTime.slice(0,4);
						let month = el.publishedTime.slice(5,7);
						let day = el.publishedTime.slice(8,10);
						el.publishedTime = year+"년 "+month+"월 "+day+"일"
						this.posts = data;
					})
				});
		},
		postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      this.$router.push({name: 'postdetail', params: {data: one_post}})
    },
	}
}
</script>

<style scoped>
span {
	top:10px;
	height: 4px;
	display: inline-block;
	background-color: #08d3bc;
  /* padding-bottom:2px; */
}
.search {
	height: 370px;
	padding-top: 170px;
	color : white;
	background-color: #6987f7;
	background-image: linear-gradient(to left,#5c7bf4 10%,#08d3bc);
}
.inner_box {
	width: 712px;
	padding: 0 92px;
	margin: 0 auto;
	min-width: 530px;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
}
.inner_box h2{
	font-weight: 800;
}
/* 검색결과 */
.content_list {
	width: 768px;
	padding: 0 15px;
	margin : 0 auto;
	min-width: 712px;
}
.postcount{
	font-size: 1.125rem;
	margin-top: 3rem;
}
.postcount h3{
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
	font-weight:600;
}
.postcount strong {
	font-weight: 800;
	font-size:2rem;
}
.post_list {
	margin : 0 auto;
	padding: 2rem 0 64px 0;
	border-bottom : 1px solid #ddd;
	cursor:pointer;
}
.profile {
	display : flex;
	align-items: center;
	margin-bottom: 1.5rem;
}
.profile a {
	color : black;
	margin-right: 1rem;
}
.profile img  {
	display: block;
	width: 3rem;
	height: 3rem;
	border-radius: 1.5rem;
}
.post_info h2{
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
	font-weight: 800;
	font-size: 1.5rem;
}
.post_content {
	margin : 0.5rem 0 2rem 0;
	line-height: 1.3rem;
	max-height: 3.9rem;
	overflow:hidden;
	text-overflow: ellipsis;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
}
.post_thumbnail{
	width:100%;
	margin-bottom: 1rem;
	height: 350px;
	overflow: hidden;
}
.post_thumbnail img{
	width:100%;
	height: 100%;
	object-fit: cover;
}
</style>