<template>
    <div id="Detail">
        <Navbar></Navbar>
        
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