<template>
  <div id="app">
  <!-- navbar(로고에 개인 블로그 이름 추가되야함) -->
  <Navbar/>
  <div class="detail_content">
    <h1 class="post-title">{{ post.title }}</h1>
    <div class="btn_outer">
      <div class="likepost">
        <div class="remote_likepost">
          <v-btn icon fab outlined depressed large @click="addToFeed" color="pink">
            <v-icon :class="like"></v-icon>
          </v-btn>
        </div>
      </div>
    </div>
    <div class="post_info">
      <div class="post_author">
        {{post.author}}
      </div>
      <div class="post_date">
        {{post.publishedTime}}
      </div>
    </div>
    <div class="taglist">
      <PostTagBox
          :tagList="post.tag"
        />
    </div>
    <div class="post-content">
      <!-- TextEditor 미리보기만(마크다운) -->
      <!-- <Viewer :initialValue="this.post.content"/> -->
      <Viewer 
        :initialValue="this.post.content"
        @change="onEditorChange"
      />
    </div>
    <!-- 작성자 Thumnail, 자기 소개, 후원버튼 & 후원QR -->
    <div class="author_info">
      <div class="author_img">
        <img :src="post.authorInfo.profileImage">
      </div>
      <div class="author_prof">
        <span>{{ post.author }}</span>
        <div v-if="post.authorInfo.introduce">
        {{ post.authorInfo.introduce }}
        </div>
        <div v-else>
          안녕하세요, {{ post.author }}입니다.
        </div>
        <!-- 소셜 로그인 버튼 -->
        <div class="social-login">
          <i v-if="post.authorInfo.github" small class="fab fa-github fa-2x mr-3" href="`https://github.com/${post.authorInfo.github}`"></i>
          <i v-if="post.authorInfo.instagram" small class="fab fa-instagram fa-2x mr-3" href=""></i>
          <i v-if="post.authorInfo.facebook" small class="fab fa-facebook fa-2x mr-3" href=""></i>
        </div>
      </div>
    </div>
    <div class="author_dona">
      <div class="dona_btn" v-if="post.authorInfo.qrImage">
        <v-btn
          tile
          depressed
          outlined
          color="#08d3bc"
          @click="ClickQrBtn" v-if="!qrflag"
        >글쓴이에게 후원하기</v-btn>
        <div v-if="qrflag" class="qrimage-resize" @click="ClickQrBtn">
          <v-img :src="post.authorInfo.qrImage"></v-img>
        </div>
      </div>
      <div v-else></div>
    </div>
    
    <div class="comment_area">
      <div class="comment_input">
        <v-textarea
          clearable
          flat
          outlined
          hide-details
          label="댓글 내용을 작성하세요"
          rows="3"
          v-model="CommentInput"
          style="border-radius:0px"
        ></v-textarea>
        <div class="comment_btn">
          <v-btn
            depressed
            color="#08d3bc"
            @click="onCommentCreate"
            style="border-radius:0px"
            class="white--text"
          >댓글 작성
          </v-btn>
        </div>
      </div>
      <div class="comment_p" v-if="renderComponent">
          <div v-for="comment in Comments" :key="comment.cid">
            <Comment 
              :comment="comment"
              @Click-Delete-Btn="CommentRead"
              @Click-Update-Btn="CommentRead"
              @Child-Create="CommentRead"
              @Click-Child-Change-Btn="CommentRead"
            />
        </div>
      </div>
    </div>
    <v-row>
      
      <!-- 게시글, 댓글 큰틀  -->
      <v-col cols="10" class="pa-0">  
        <v-layout column="12">
          <!-- 게시글 헤더 -->
        
          <!-- 게시글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
          <v-col v-if="post.isauthor" cols="12">
            <v-btn @click="onPostUpdateBtn" color="success">게시글 수정</v-btn>
            <v-btn @click="onPostDeleteBtn" color="primary">게시글 삭제</v-btn>
          </v-col>
          <!-- 블로그 작성자 Thumnail -->

          <!-- 댓글 목록 -->
          <!-- 댓글 수정, 삭제(작성자랑 일치할 경우 버튼 노출) -->
          
        </v-layout>
      </v-col>
    </v-row>
  </div>
  </div>
</template>

<script>
import Navbar from './Navbar.vue'
import Comment from './Comment.vue'
import PostTagBox from './PostTagBox.vue'

import { Viewer } from '@toast-ui/vue-editor'
import axios from 'axios'

const storage = window.sessionStorage
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
    name: 'postdetail',
    components: {
      Viewer,
      Navbar,
      Comment,
      PostTagBox,
    },
    data() {
        return {
          post: {
            title: '',
            author: '',
            content: '',
            publishedTime: '',
            // islike: this.$route.params.data.isLike,
          },
          // content: this.$route.params.data.content,
          Comments: null,
          CommentInput: '',
          ChildCommentInput: '',
          FeedFlag: '',
          like: '',
          renderComponent: true,
          qrflag: false,
        }
    },
    created(){
      axios.post(API_URL + 'api/v2/p/',{
        uid : storage.getItem("uid"),
        pid : storage.getItem("pid")
      })
        .then(res => {
          this.post = res.data
          this.post.title = res.data.title
          this.post.author = res.data.author
          this.post.content = res.data.content
          this.post.islike = res.data.isLike
          let year = res.data.publishedTime.slice(0,4);
          let month = res.data.publishedTime.slice(5,7);
          let day = res.data.publishedTime.slice(8,10);
          this.post.publishedTime = year+"년 "+month+"월 "+day+"일"
          
          if(this.post.islike===true){
            this.like = "fas fa-heart"
            this.FeedFlag= true
          } else {
            this.like = "far fa-heart"
            this.FeedFlag = false
          }
          this.Comments = res.data.comments
          this.isCommentauthor(this.Comments)
          this.post.isauthor = this.isPostauthor()
          console.log(this.post)
      })
    },
    mounted() {
      this.fetchData()
    },
    methods: {
      forceRerender() {
        this.renderComponent = false;
        this.$nextTick(() => {
          this.renderComponent = true;
        })
      },
      fetchData() {
        axios.post(API_URL + 'api/v2/p/',{
          uid : storage.getItem("uid"),
          pid : storage.getItem("pid")
        })
          .then(res => {
            this.post = res.data
            this.Comments = res.data.comments
            this.isCommentauthor(this.Comments)
            this.post.isauthor = this.isPostauthor()
          })
      },
      onEditorChange() {
        this.fetchData()
      },
      // 글 작성자인지 확인
      isPostauthor() {
        if (this.post.author === storage.getItem("login_user") && this.post.uid === Number(storage.getItem("uid"))) {
          return true
        } else {
          return false
        }
      },
      // 게시글 수정 페이지로 보내는 버튼
      onPostUpdateBtn() {
        if (this.isPostauthor()) {
          console.log()
          // this.$router.push({path:`/${}/${}`})
        }
      },

      // 게시글 삭제 버튼
      onPostDeleteBtn() {
        if (this.isPostauthor()) {
          axios.delete(API_URL + 'api/v2/' + this.post.pid)
            .then(() => {
              alert('게시글이 삭제되었습니다.')
              this.$router.push('/')
          })
        }
      },
      
      // 댓글 Create 메서드
      onCommentCreate() {
        if (storage.getItem("login_user")) {
          if (this.CommentInput.trim()) {
            const tmp_comment = {
              author: storage.getItem("login_user"),
              content: this.CommentInput,
              uid: storage.getItem("uid"),
              pid: storage.getItem("pid")
            }
            // 서버에 댓글 작성 요청 보냄
            axios.post(API_URL + 'api/v3/', tmp_comment)
              .then(() => {
                // 댓글 작성 완료 후 새로 댓글 받아옴
                this.CommentRead()
              })
          } else {
            alert('빈 댓글은 작성할 수 없습니다.')
          }
        } else {
          alert('로그인한 유저만 댓글을 달 수 있습니다.')
        }
      },
      // 해당 Post의 모든 Comment 읽어오기 & 인풋 초기화
      CommentRead() {
        axios.get(API_URL+'api/v3/'+storage.getItem("pid"))
          .then(res => {
            this.Comments = this.isCommentauthor(res.data)
            this.forceRerender()
          })
        this.CommentInput = ""
      },
      // 작성자 비교 후 삭제 버튼 flag 설정하는 메서드
      isCommentauthor(Comments) {
        Comments.forEach(one_comment => {
          if (one_comment.author === storage.getItem("login_user") && one_comment.uid === Number(storage.getItem("uid"))) {
            one_comment.isauthor = true
          } else {
            one_comment.isauthor = false
          }
          if (one_comment.child.length) {
            one_comment["child"].forEach(one_child => {
              if (one_child.author === storage.getItem("login_user") && one_child.uid === Number(storage.getItem("uid"))) {
                one_child.isauthor = true
              } else {
                one_child.isauthor = false
              }
            })
          }
        })
        return Comments
      },
      //좋아요에 포스트를 담거나 빼는 메서드
      addToFeed(){
        //로그인 체크
        if(storage.getItem("login_user")){
          //이미 피드에 있는지 체크
          if(this.FeedFlag===false){
            this.FeedFlag=true;
            this.post.islike=true;
            this.like="fas fa-heart"
            axios
            .put(API_URL+'api/v2/likes',{
              uid : storage.getItem("uid"),
              pid : storage.getItem("pid"),
              status : this.FeedFlag,
            })
            .then(function(response){
              console.log(response);
              })
            .catch(function(error){
              console.log(error);
            })
          }
          //삭제하면 db에서 제거
          else {
            this.like="far fa-heart"
            this.post.islike=false;
            this.FeedFlag=false;
            axios.put(API_URL+'api/v2/likes',{
              uid : storage.getItem("uid"),
              pid : storage.getItem("pid"),
              status : this.FeedFlag,
            })
            .then(function (response){
              console.log(response);
            })
            .catch(function(error){
              console.log(error);
            });
          }
        }
        else {
          alert('로그인 후 이용해주세요.')
        }
      },
      ClickQrBtn() {
        this.qrflag = !this.qrflag
      }
    }
}
</script>

<style>
.btn_outer{
  position: relative;
  margin-top: 2rem;
}
.likepost{
  position: absolute;
  left: 64rem;
}
.remote_likepost{
  position: fixed;
}
.detail_content {
  padding-top:151px;
  max-width:980px;
  margin: 0 auto;
}
.post_info{
  display: flex;
}
.post_author{
  font-family: 'Inter', sans-serif;
  font-weight: 700;
  font-size: 16px;
  padding-right: 30px;
}
.post_date{
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 14px;
  margin: auto 0;
}
.taglist{
  margin: 10px 0;
}
.post_content{
  height:700px;
}
.author_dona{
  width: 100%;
  margin-top: 42px;
}
.dona_btn{
  margin-top:-123px;
  float: right;
}
.qrimage-resize {
  margin-top:-63px;
  width: 10rem;
  height: 10rem;
  overflow: hidden;
}

.test {
  display: flex
}
.post-box {
  margin-top: 130px;
}
.social-login{
  padding-top: 10px;
  color:#ccc
}
.post-title {
  margin: 0 0 2rem 0;
	font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 36px;
  font-weight: 800;
}
.post-content {
  min-height:500px;
  margin: 2rem 0 2rem 0;
  padding: 0 0 1rem 0;
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
}
.author_info {
  display: flex;
}
.author_img{
  width: 128px;
  height: 128px;
}
.author_img img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 1px solid #ddd;
}
.author_prof{
  padding-top:10px;
  padding-left: 15px;
  margin: auto 0;
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 20px;
  line-height: 28px;
}
.author_prof span{
  font-size: 32px;
  font-weight: 800;
}
.comment_btn {
  display:flex;
}
.comment_input button {
  margin-top: 10px;
  margin-bottom: 20px;
  margin-left: auto;
}
.comment-header {
  margin: 2rem 0 2rem 0;
  padding: 0 0 1rem 0;
  border-bottom: 1.5px solid #888888
}

.author_profile_img {
  border-radius: 50%;
} 
.tui-editor-contents p{
  font-size: 1.125rem;
}
.tui-editor-contents li{
  font-size: 1.125rem;
}
.tui-editor-contents h1{
  font-size: 32px;
  font-family: 'inter', 'NanumSquare','나눔스퀘어', sans-serif;
  line-height:35px;
}
.tui-editor-contents h2{
  font-size: 24px;
  font-family: 'inter', 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-weight: 700;
  line-height:30px;
}
</style>