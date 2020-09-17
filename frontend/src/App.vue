<template>
  <v-app>
    <!-- <router-view :key="someVariableUnderYourControl"/> -->
    <router-view :key="$route.fullPath"/>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  components: {
  },
  data() {
    return {

    }
  },
}
</script>

<style>
</style>
