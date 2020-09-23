<template>
    <div id="Detail">
        <Navbar></Navbar>
        <h1>상세페이지</h1>
        <tr>
			<td>{{name}}</td>
		</tr>
		    <tr>
				<td class="txt_cont" v-html="cont"></td>
			</tr>
    </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'

export default {
    name: 'detail',
    components:{
        Navbar
    },
    data(){
        return{
            id:null,
            name:null,
            branch:null,
            area:null,
            tel:null,
            
        }
    }
}
</script>