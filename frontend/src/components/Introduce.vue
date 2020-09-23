<template>
  <div>
		<Navbar></Navbar>
		<div class="wrap">
			<div id="spot" class="spot">
				<div class="inner">
					<h2 class="spot_title">3RD GENERATION PROJECT <span style="color:#08d3bc">BLOG</span> FOR DEVELOPER</h2>
					<div class="spot_desc_box">
						<strong class="point"><span style="font-family: 'Inter', sans-serif;font-weight: 800;">DO!</span>는 개발자를 위한 블로그입니다</strong><br>
						오늘의 개발은 어떠셨나요? 이슈는 없으셨나요?<br>
						모두 <span style="font-family: 'Inter', sans-serif;font-weight: 800;color:#08d3bc">DO!</span>에 기록해보세요!
					</div>
				</div>
			</div>
			<div class="contents">
				<div id="section_contents" class="section_contents effect">
					<div class="inner">
						<h3 class="section_title"><span style="font-family: 'Inter', sans-serif;font-weight: 800;color:#08d3bc">DO!</span>가 제공하는<br> 놀라운 서비스</h3>
						<ul class="contents_container">
							<li class="contents_list">
								<div class="slide_item">
									<strong class="content_sort">조회순? 인기순? 최신순?</strong>
									<h4 class="content_title">오늘은 또 무슨 포스트가?</h4>
									<p class="contents_desc">
										DO!에 기록되는 다양한 콘텐츠를<br> 
										보여드리기 위한 세 가지 정렬 방식!<br>
										취향에 맞게 골라보세요.
									</p>
								</div>
							</li>
							<li class="contents_list">
								<div class="slide_item">
									<strong class="content_sort">내 피드 안에 저장</strong>
									<h4 class="content_title">보고 싶은 것만 담아 보자</h4>
									<p class="contents_desc">
										자주 보고 싶고, 오래 보고 싶은 포스트는<br>
										북마크를 눌러 피드에 담아두세요.
									</p>
								</div>
							</li>
							<li class="contents_list">
								<div class="slide_item">
									<strong class="content_sort">코드</strong>
									<h4 class="content_title">당신을 위해 준비했습니다</h4>
									<p class="contents_desc">
										DO!는 가독성 좋은 코드 포스팅을 위해<br> 
										마크다운 에디터를 제공합니다.<br>
										거기에 WYSIWYG까지!<br> 
										마음껏 당신의 이야기를 기록하세요. 
									</p>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div id="section_madeby" class="section_madeby effect">
					<div class="inner">
						<h3 class="section_title">
							KEEP <span style="font-family: 'Inter', sans-serif;font-weight: 800;color:#08d3bc">DO</span>ING<br>
						</h3>
							<span class="section_desc">4명이 제공하는 블로그 서비스 DO!<br>
							우주호, 안시원, 김현성, 김민하가 함께합니다.</span>
					</div>
				</div>
				<!-- <div id="section_reg" class="section_reg">
					<a>
						회원가입
					</a>
				</div> -->
			</div>
		</div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import AOS from 'aos'
import 'aos/dist/aos.css'

AOS.init();

export default {
    name: 'Introduce',
    components: {
        Navbar,
    },
    data() {
        return {
            
        }
    }
}
</script>

<style scoped>
@import url(https://cdn.jsdelivr.net/gh/moonspam/NanumSquare@1.0/nanumsquare.css);

h2 {
	display: block;
	font-size:4.7em;
	margin-block-start: 0.83em;
	margin-block-end: 0.83em;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
}
h4{
	display: block;
	margin-block-start: 1.33em;
	margin-block-end: 1.33em;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
}
ul, li {
	list-style: none;
}
ul {
	display: block;
	margin-block-start: 1em;
	margin-block-end: 1em;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
	padding-inline-start: 40px;
}
a{
	text-decoration: none;
}
.spot {
	height:800px;
}
.spot .spot_title{
	position: absolute;
	left: 0;
	top:180px;
	width:100%;
	height:149px;
	background-size: 762px 149px;;
}
.spot .inner {
	text-align: center;
	position: relative;
	width: 960px;
	height:100%;
	margin: 0 auto;
}
.point {
	color: #08d3bc;
	font-weight: normal;
}
.spot .spot_desc_box {
	line-height: 1.5;
	position: absolute;
	left:0;
	top: 520px;
	width: 100%;
	padding-top:19px;
	font-size:30px;
	color:#000;
	text-align: left;
	font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
}
.section_contents{
	position:relative;
	height:730px;
	background-color: #f2f4f7;
}
.section_contents .inner {
	position: relative;
	z-index: 1;
	width:960px;
	height: 100%;
	margin: 0 auto;
	padding: 204px 0 160px;
	box-sizing: border-box;
}
.section_contents .section_title {
	position: absolute;
	left: 0;
	top: 100px;
	padding-top: 30px;
	font-size: 42px;
	line-height:1.4;
	font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
}
.section_contents .section_title::before{
	content: '';
	position:absolute;
	left:248px;
	top:130px;
	width:30px;
	height:3px;
	background-color: #000;
	animation: .8s blink infinite;
}
.section_contents .contents_list:first-child{
	right: 0;
	top: 130px;
	animation-delay: .5s;
}
.section_contents .contents_list:nth-child(2){
	left: 0;
	top: 313px;
	animation-delay: 1.1s;
}
.section_contents .contents_list:nth-child(3){
	right: 0;
	top: 490px;
	animation-delay: 1.7s;
}
.section_contents .contents_list{
	position: absolute;
	width: 460px;
	background-color:#fff;
	box-shadow: 30px 30px 40px 0 rgba(0, 0, 0, 0.3); 
}
.section_contents .contents_list .slide_item{
	display: block;
	padding: 48px;
	box-sizing: border-box;
	font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
}
.section_contents .contents_list .content_sort {
	color:#08d3bc;
	font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 18px;
	font-weight:400;
	line-height:1.33;
}
.section_contents .contents_list .content_title{
	margin: 8px 0 44px;
	font-family:'NanumSquareEB','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
	font-weight: 800;
	font-size: 28px;
	line-height: 1.5;
}
.section_contents .contents_list .contents_desc{
	font-family:'NanumSquareEB','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 18px;
	color: #767676;
	line-height: 1.56;
}

.section_madeby {
	height: 1160px;
	background-image: url(https://miro.medium.com/max/11014/1*xAFAiAxqZVrOVLBTo9tf6w.jpeg);
	background-size: cover;
	background-position: 80% 100%;
}
.section_madeby .inner{
	position: relative;
	z-index: 1;
	width: 960px;
	margin: 0 auto;
	height: 100%
}
.section_madeby .section_title {
	position: absolute;
	left: 0;
	top: 90px;
	padding-top: 30px;
	font-size: 42px;
	line-height:1.4;
	font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
}
.section_madeby .section_title:before {
	content: '';
	position: absolute;
	left: 248px;
	top: 75px;
	width: 26px;
	height: 3px;
	background-color: #000;
	animation: .8s blink ease infinite;
}
.section_madeby .section_desc{
	position: absolute;
	font-size: 20px;
	line-height:26px;
	top: 180px;
}
/* .btn_apply {
	display: inline-block;
	min-width: 260px;
	height: 64px;
	background-color: #08d3bc;
	border-radius: 32px;
	box-sizing: border-box;
	font-family: 'NanumSquareEB','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
	font-size: 20px;
	color: #fff;
	line-height: 64px;
	text-align: center;
	text-decoration: none;
}
.section_reg .btn_apply{
	position:fixed;
	left: 50%;
	margin-left:-140px;
	border: 0.7px solid rgba(0, 255, 152, 0.5);
	border-radius: 36px;
	bottom:80px;
	z-index: 10;
	box-shadow: 0 6px 14px 0 rgba(0, 255, 152, 0.4);
	animation: .8s;
}
.section_reg .btn_apply .fixed{
	position:fixed;
	bottom:48px;
	z-index: 10;
	animation: .8s;
} */

</style>