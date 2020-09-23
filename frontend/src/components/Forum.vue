<template>
  <div>
    <Navbar></Navbar>
      <div class="doContent">
         <div class="feature_feed">
            <div class="inner_feature">
               <h2 id="do_top do_feed">Forum</h2>
               <p class="top_desc"><span style="font-style:'Inter', sans-serif; font-weight:800">DO!</span>의 모든 태그들을 만나보세요</p>
            </div>
         </div>
         <div id="app" class="innner">
            <cloud 
               :data="words" 
               :fontSizeMapper="fontSizeMapper"
               :colors="colors"
               @submit-tagname="searchtagname"
            />
         </div>
         <div class="postcount">
				<!-- kword에 밑줄 어떻게 긋지? -->
            <div class="tagcnt" v-if="tagclick">
               <h3 style="font-size:52px"><span style="color:#08d3bc;">#</span>{{tag.text}}</h3>
               총 <strong>{{posts.length}}</strong>개의 포스트
            </div>
			</div>
         <div class="taglist" v-if="posts.length>0">
            <div @click="postdetail(post)" class="onetag" v-for="post in posts" :key="post.pid">
               <div class="profile">
                  <a><img :src="post.authorInfo.profileImage"></a>
                  <div class="nickname">
                     {{post.author}}
                  </div>
               </div>
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
import Cloud from '../components/Cloud.vue'
import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'
// import Cloud from 'vue-d3-cloud'

AOS.init();

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
   name: 'Forum',
   components: {
      Navbar,
      Cloud
   },
   props:{
      onWordClick:{
         type: Function,
         default:()=>{

         }
      }
   },
   data() {
      return {
         tab: null,
         words: [],
         colors:["ffbe0b","628AF8","8581E6","A879D4","CB70C1","EE67AF","FF63A6","fb5607","ff006e","8338ec","3a86ff","11D9C3","23C7CF","f8961e","f9c74f","90be6d","43aa8b","EE67AF","FF63A6","11D9C3","23C7CF","35B6DB","47A4E6"],
         // ["11D9C3","23C7CF","f8961e","f9c74f","90be6d","43aa8b","EE67AF","FF63A6","11D9C3","23C7CF","35B6DB","47A4E6"],
         // ["11D9C3","23C7CF","35B6DB","47A4E6","5993F2"
         // ,"628AF8","8581E6","A879D4","CB70C1","EE67AF","FF63A6"],
         fontSizeMapper: word => Math.log2(word.value) * 3,
         spiral: "archimedean",
         tag: '',
         tagclick: false,
         posts : {
            pid: '',
            title: '',
            content: '',
            publishedTime: '',
            thumbnail:'',
         },
         profileImg: '',
         basicthumb: 'http://i3a507.p.ssafy.io/img/default/thumbnail.jpg'
      }
   },
   created(){
      this.profileImg = storage.profileImage;
      var vm = this;
      axios.get(API_URL+"api/v4")
      .then(function(response) {
        vm.words = response.data;
      })
      .catch(function(err) {
        console.log(err);
      })
   },
   computed: {
      
   },
   mounted(){
      this.onWordClick();
   },
   methods: {
      searchtagname(name){
         this.tagclick = true;
         this.tag = name;
         axios.get(API_URL+'api/v4/'+name.text)
         .then(({data})=>{
            // 콘텐츠 미리보기 슬라이스
            data.forEach(el => {
            el.tmp = el.content
            if (el.content.length > 40) {
               // 마크다운 사진 제외
               el.content = el.content.replace(/!\[.*\)+/, "")
               // 최대 길이 슬라이스
               el.content = el.content.slice(0, 40)
            }
            // 작성 날짜만 보이게 수정
            let year = el.publishedTime.slice(0,4);
            let month = el.publishedTime.slice(5,7);
            let day = el.publishedTime.slice(8,10);
            el.publishedTime = year+"년 "+month+"월 "+day+"일"
            // this.posts = data
            });
            this.posts=data
         })
      },
      postdetail(one_post) {
         storage.removeItem("pid")
         storage.setItem("pid", one_post.pid)
         this.$router.push({name: 'postdetail', params: {data: one_post}})
      },
   },
}
</script>

<style>
.feature_feed{
  height: 133px;
  padding: 151px 0 110px;
  border-bottom: solid 2px black;;
}
.inner_feature h2 {
  font-family: 'Inter', sans-serif;
  font-size: 24px;
  font-weight: 800;
}
.top_desc {
  padding:0 0;
  font-size: 20px;
  line-height: 1;
  letter-spacing: -0.6px;
  font-family: Noto Sans Light, sans-serif;
}
.doContent {
  max-width: 1150px;
  min-width: 1100px; 
  margin: 0 auto 0;
  padding: 0 20px;
}
.feature_feed{
  height: 133px;
  padding: 151px 0 110px;
  border-bottom: solid 2px black;;
}
.feature_main {
  width: 100%;
  height: 133px;
  padding: 151px 0 0;
}
.innner {
   padding-top:70px;
  min-width: 1100px;
  max-width: 1150px;
   margin: 0 auto;
   text-align: center;
}
svg {
   padding: 10px 0;
   /* border: 1px solid black; */
   border-radius: 5px;

}
.wordCloud{
   text-align: center;
}
.wordCloud text{
  font-family: 'Roboto','NanumSquareR','나눔스퀘어','Noto Sans DemiLight','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif !important;
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
.onetag {
   max-width: 738px;
   margin: 0 auto;
   padding: 30px 0 50px;
   border-bottom: 1px solid #ddd;
}
.tagcnt{
   max-width: 738px;
   margin: 0 auto;
   line-height: 17px;
   padding-bottom: 10px;
}
</style>