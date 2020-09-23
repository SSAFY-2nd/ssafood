<template>
	<div>
		<Navbar></Navbar>
		<div class="wrap">
			<div class="inner">
				<div class="inner_feature">
					<h2 id="do_top do_feed">회원정보</h2>
          <p class="top_desc">
						<span style="color:#6e8af8;">{{userinfo.nickname}}</span>님의 회원 정보입니다.<br>
						회원 정보는 개인정보처리방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지 않습니다.	
					</p>
				</div>
				<div class="inner_info">
					<div class="profile_area">
						<div class="imgbox">
							<!-- 회원 프로필 이미지 디비에서 조회 -->
							<!-- [추가]이미지 변화에 대한 처리 -->
							<img v-bind:src="userinfo.profileImage" alt="profile">
						</div>
						<div class="btn_group">
							<!-- 이미지 업로드 (모달) -->
							<div class="img_btn">
								<v-btn depressed width="150px" color="#6e8af8" class="white--text"><label for="file-input" style="padding-top:9px">이미지 업로드</label></v-btn>
								<input type="file" accept="image/*" @change="uploadImage($event)" id="file-input" class="uploadimg">
							</div>
							<!-- 이미지 삭제 -->
							<div class="img_btn">
								<v-btn depressed width="150px" @click="deleteProfileImage" outlined color="#6e8af8">이미지 제거</v-btn>
							</div>
						</div>
					</div>
					<div class="inner_email">
						<div class="user_email">
							<h4 class="cate_desc">이메일</h4>
							<v-text-field
								:placeholder="userinfo.emailaddr"
								outlined 
								dense
								hide-details
								disabled
								style="border-radius:0px"
							/>
							<p class="caution">※ 이메일은 수정할 수 없습니다.</p>
						</div>
						<div class="user_pwd">
							<h4 class="cate_desc">비밀번호</h4>
							<div v-if="pwdCheck">
								<v-text-field 
									v-model="userinfo.password"
									type="password"
									outlined 
									dense
									hide-details
									style="border-radius:0px"
									class="mb-3"
								/>
							</div>
							<v-btn color="#6e8af8" depressed tile outlined class="mb-3" @click="modpwd">
								비밀번호 변경
							</v-btn>
						</div>
					</div>
					<div class="user_detail">
						<div class="nickname">
							<h4 class="cate_desc">닉네임</h4>
							<div v-if="!clickprofile">
								<v-text-field 
									outlined
									dense
									hide-details 
									disabled
									v-model="userinfo.nickname" 
									style="border-radius:0px"/>
							</div>
							<div v-else>
								<v-text-field 
									outlined
									dense
									v-model="userinfo.nickname" 
									hide-details 
									style="border-radius:0px"/>
							</div>
						</div>
						<div class="introduce">
							<h4 class="cate_desc">소개</h4>
							<div v-if="!clickprofile">
								<v-textarea
									rows="3"
									outlined
									dense
									hide-details 
									disabled
									v-model="userinfo.introduce" 
									style="border-radius:0px"
								/>
							</div>
							<div v-else>
								<v-textarea 
									rows="3"
									outlined
									dense 
									hide-details 
									v-model="userinfo.introduce" 
									style="border-radius:0px"
								/>
							</div>
						</div>
						<div class="mod_btn">
							<div v-if="!clickprofile" class="btn_mod">
								<v-btn color="#6e8af8" depressed tile outlined @click="modprofile">수정</v-btn>
							</div>
							<div v-else class="btn_mod">
								<v-btn color="#6e8af8" depressed tile outlined @click="updateNickname">수정완료</v-btn>
							</div>
						</div>
					</div>
					<div class="user_sns">
						<h4 class="cate_desc">소셜 정보</h4>
						<div class="user_sns_detail" v-if="!clicksns">
							<div v-if="userinfo.github" class="sns_addr">
								<v-icon style="padding-right:5px" small>fab fa-github</v-icon>
								https://github.com/{{userinfo.github}}
							</div>
							<div v-if="userinfo.instagram" class="sns_addr">
								<v-icon style="padding-right:5px" small>fab fa-instagram</v-icon>
								https://instagram.com/{{userinfo.instagram}}
							</div>
							<div v-if="userinfo.facebook" class="sns_addr">
								<v-icon style="padding-right:5px" small>fab fa-facebook</v-icon>
								https://facebook.com/{{userinfo.facebook}}
							</div>
						</div>
						<div class="user_sns_detail" v-else>
							<div>
								<v-text-field 
								outlined
								dense
								style="border-radius:0px"
								prepend-icon="fab fa-github" 
								v-model="userinfo.github"
								hint="Github address"
								prefix="https://github.com/"
								></v-text-field>
							</div>
							<div>
								<v-text-field 
								outlined
								dense
								style="border-radius:0px"
								prepend-icon="fab fa-instagram" 
								v-model="userinfo.instagram"
								hint="Instagram address"
								prefix="https://instagram.com/"
								></v-text-field>
							</div>
							<div>
								<v-text-field 
								outlined
								dense
								style="border-radius:0px"
								prepend-icon="fab fa-facebook"
								v-model="userinfo.facebook"
								hint="Facebook address"
								prefix="https://facebook.com/"
								></v-text-field>
							</div>
						</div>
						<div class="mod_btn">
							<div v-if="!clicksns">
								<v-btn color="#6e8af8" depressed tile outlined @click="modSNS">수정</v-btn>
							</div>
							<div v-else>
								<v-btn color="#6e8af8" depressed tile outlined @click="updateSNS">수정완료</v-btn>
							</div>
						</div>
					</div>
					<div class="inner_detail">
						
						
						<div class="dona_info">
							<h4 class="cate_desc">후원 QR</h4>
							<div class="qrbox">
								<div class="qrbox_img">
									<div v-if="userinfo.qrImage===null">
										<img src="http://i3a507.p.ssafy.io/img/user/default/qr.png">
									</div>
									<div  v-else>
										<img v-bind:src="userinfo.qrImage" onerror="this.src='http://i3a507.p.ssafy.io/img/user/default/qr.png'">
									</div>
								</div>
							</div>
							<div class="btn_grp">
								<div class="img_btn">
									<v-btn depressed width="150px" color="#6e8af8" class="white--text"><label for="qr-input" style="padding-top:9px">이미지 업로드</label></v-btn>
									<input type="file" accept="image/*" @change="uploadQR($event)" id="qr-input" class="uploadimg">
								</div>
								<div class="img_btn">
									<v-btn depressed outlined width="150px" @click="deleteQRImage" color="#6e8af8">이미지 제거</v-btn>
								</div>
							</div>
						</div>
					</div>
					<div class="signout">
						<a @click="showMsgBoxTwo">DO!에서 <span class="signout_btn">탈퇴하기</span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
const storage = window.sessionStorage

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
	components: {
		Navbar,
	},
	props: {
		iconName: {
		type: String,
		default: 'box'
		},
		width: {
		type: [Number, String],
		default: 18
		},
		height: {
		type: [Number, String],
		default: 18
		},
		iconColor: {
		type: String,
		default: 'currentColor'
		}
	},
	data() {
		return {
			pwdCheck: false,
			clickprofile : false,
			clicksns : false,
			mandatory : false,
			commentAlarm : false,
			updateAlarm : false,
			boxTwo: '', // 회원 탈퇴
			userinfo : {
				uid : '',
				password: '',
				profileImage : '',
				nickname : '',
				introduce : '',
				blogtitle : '',
				github : '',
				facebook : '',
				instagram : '',
				emailaddr : '',
				qrImage : ''
			},
			accountinfo : {

			},
			item:{
				image : null,
				imageUrl: null,
				previewImage:null
			},
			rules: [
				value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
			],	
		}
	},
	methods: {
		
		modprofile() {
			if(this.clickprofile===false){
				this.clickprofile = true;
			}
		},
		modSNS(){
			if(this.clicksns===false){
				this.clicksns = true;
			}
		},
		modpwd(){
			if(this.pwdCheck===false){
				this.pwdCheck=true;
			}
			else if(this.pwdCheck===true){
				this.pwdCheck=false;
				axios.put(API_URL+'api/v1/'+this.userinfo.uid,{
					password : this.userinfo.password,
					updateType : "1"
				}).then(function(response){
					console.log(response);
				}).catch(function(error){
					console.log(error);
				})
			}
		},

		// nickname, introduce 수정
		updateNickname() {
			if(this.clickprofile===true){
				this.clickprofile = false;
				axios.put(API_URL+'api/v1/'+this.userinfo.uid,{
					email : this.userinfo.emailaddr,
					nickname : this.userinfo.nickname,
					introduce : this.userinfo.introduce,
					updateType : "1" 
				})
				.then(function (response){
					console.log(response);
				})
				.catch(function(error){
					console.log(error);
				})

			}
		},

		// sns 계정 수정
		updateSNS() {
			if(this.clicksns===true){
				this.clicksns = false;
				axios.put(API_URL+'api/v1/{uid}',{
					email : this.userinfo.emailaddr,
					instagram : this.userinfo.instagram,
					github : this.userinfo.github,
					facebook : this.userinfo.facebook,
					updateType : "2" 
				})
				.then(function (response){
					console.log(response);
				})
				.catch(function(error){
					console.log(error);
				})
			}
		},

		// 업로드 이미지
		uploadImage(e){
			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			// 미리보기용
			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.userinfo.profileImage = e.target.result; // profileImage 설정
			};

			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v1/img?uid='+this.userinfo.uid,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
			)
			.then(response => {
				this.userinfo.profileImage = response.data;
				storage.setItem("profileImage", response.data)
			})
			.catch((err) => {
				console.log("프로필 이미지 업로드 실패")
				console.log(err);
			})

		},
		// 기본 이미지로 수정
		deleteProfileImage(){
	
			
			// **추가** POST문으로 서버에 기본 이미지 파일 전송
			axios.put(API_URL+'api/v1/img/'+this.userinfo.uid,{
				url : "http://i3a507.p.ssafy.io/img/common/emptyProfile.png",
			})
			.then(response => {
				this.userinfo.profileImage = response.data;
				storage.setItem("profileImage", response.data)
			})
			.catch( (err) => {
				console.log("프로필 이미지 삭제 실패");
				console.log(err)
			})

		},
		
		// 업로드 QR코드
		uploadQR(e){
			var file = new FormData();
			const image = e.target.files[0];
			file.append("file", image);

			const reader = new FileReader();
			reader.readAsDataURL(image); 
			reader.onload = e =>{
				this.userinfo.qrImage = e.target.result; // profileImage 설정
			};
			
			// **추가** POST문으로 서버에 이미지 파일 전송
			axios.post(API_URL+'api/v1/qr/?uid='+this.userinfo.uid,
				file,
				{
					headers:{
						'Content-Type': 'multipart/form-data',
						'Accept': 'application/json'
					}	
				}
			)
			.then(response => {
				this.userinfo.qrImage = response.data;
			})
			.catch( (err) => {
				console.log("QR 이미지 업로드 실패");
				console.log(err);
			})

		},
		deleteQRImage(){
			
			// **추가** POST문으로 서버에 기본 이미지 파일 전송
			axios.put(API_URL+'api/v1/qr/'+this.userinfo.uid,{
				url : "http://i3a507.p.ssafy.io/img/common/emptyQR.jpg",
			})
			.then( response => {
				this.userinfo.qrImage = response.data;
			})
			.catch( (err) => {
				console.log("QR 이미지 삭제 실패")
				console.log(err);
			})
		
		},
		// 회원탈퇴 토글 -> 모달창
		handle_toggle: function(){ 
			this.is_show = !this.is_show; // #2, #3
		},
		showMsgBoxTwo() {
			this.boxTwo = ''
			this.$bvModal.msgBoxConfirm('탈퇴 시 작성하신 포스트 및 댓글이 모두 삭제되며 복구되지 않습니다.', {
				title: '회원탈퇴',
				size: 'lg',
				buttonSize: 'lg',
				okVariant: 'danger',
				okTitle: '네, 탈퇴하겠습니다.',
				cancelTitle: '아니요',
				footerClass: 'p-2',
				hideHeaderClose: false,
				centered: true
			})
			.then(value => {
				this.boxTwo = value
				if(value === true){
					axios.delete(API_URL+'api/v1/'+this.userinfo.uid)
					.then(function (response){
						console.log(response);
					})
					.catch(function(error){
						console.log(error);
					});
				}
				this.$router.push('/')
			})
			.catch(err => {
			// An error occurred
				console.log(err)
			})
		}
	},
	created(){
		// String userinfo = storage.get("")
		var uid = sessionStorage.uid;
		axios
		.get(API_URL+'api/v1/'+uid)
		.then(({data})=>{
			this.userinfo.uid = data.uid;
			this.userinfo.profileImage = data.profileImage;
			this.userinfo.nickname = data.nickname;
			this.userinfo.introduce = data.introduce,
			this.userinfo.github = data.github,
			this.userinfo.facebook = data.facebook,
			this.userinfo.instagram = data.instagram,
			this.userinfo.emailaddr = data.email,
			this.userinfo.qrImage = data.qrImage
			this.userinfo.password = data.password
		})
	},
	
}
</script>

<style scoped>
	.wrap {
		background-color: #f2f4f7;	
	}
	.inner {
		min-height:1850px;
		max-width:960px;
		margin: 0 auto;
		padding: 151px 15px 0 15px;
	}
	.inner_feature h2 {
  font-family: 'NanumSquare', Noto Sans Light, sans-serif;
  font-size: 24px;
  font-weight: 800;
	}
	.inner_feature p {
		font-size: 15px;
		line-height: 19px;
		color: #666;
	}
	.inner_info {
		width:100%;
		border: 1px solid #e0e5ee;
		background-color: white;
		margin-bottom: 10px;
	}
	.inner_email{
		width:100%;
		padding: 30px;
		border-bottom: 1px solid #e0e5ee;
	}
	.user_email{
		padding-bottom: 40px;
	}
	.user_detail{
		width:100%;
		height:340px;
		padding:30px;
		border-bottom: 1px solid #e0e5ee;
	}
	.user_sns{
		width:100%;
		height:100%;
		padding:30px 30px 65px 30px;
		border-bottom: 1px solid #e0e5ee;
	}
	.profile_area{
		padding: 30px 20px 18px;
		border-bottom: 1px solid  #e0e5ee;
	}
	.imgbox {
		width: 100px;
		height: 100px;
		border-radius: 50%;
		overflow: hidden;
		margin: 0 auto;
	}
	.imgbox img {
		width : 100%;
		height: 100%;
		object-fit: cover;
	}
	.qrbox{
		width:200px;
		height:200px;
		overflow: hidden;
		margin: 0 auto;
	}

	.qrbox_img img {
		width: 100%;
		height: 100%;
	}
	.btn_group{
		margin: 15px 0 0;
	}
	
	.img_btn{
		font-family: 'NanumSquare', Noto Sans Light, sans-serif;
		width:150px;
		margin: 5px auto 0;
	}
	.inner_detail{
		width: 100%;
		padding: 30px;
	}
	.cate_desc{
		font-family: 'NanumSquare', Noto Sans Light, sans-serif;
		font-weight: 600;
		font-size: 1rem;
	}
	.caution{
		font-weight:400;
		font-size:0.8rem;
		margin: 5px 0 0 0;
	}
	.nickname {
		margin-bottom: 40px;
	}
	.introduce {
		margin-bottom: 20px;
	}
	button {
		border-radius: 0px;
	}
	button:hover{
		background-color:#6e8af8;
		color:white !important; 
	}
	.sns_addr{
		padding-bottom: 10px;
	}
	.user_sns_detail{
		margin-top:15px;
	}

	.uploadimg {
		display:none;
	}
	.circle {
		width:8rem;
		height:8rem;
		border-radius: 50%;
		overflow: hidden;
	}
  img {
		display: block;
		margin: 0 auto;
		max-height: 100%
	}
	.mod_btn{
		float:right;
	}
	.btn_grp{
		margin-top:15px;
	}
	.signout {
		float: right;
		font-family: Noto Sans Light, sans-serif;
		font-size: 13px;
		color: gray;
	}
	.signout_btn{
		text-decoration: underline;
		padding-right: 5px;
	}
</style>