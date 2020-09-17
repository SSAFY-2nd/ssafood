<template>
    <div>
		<v-chip class="mr-2 white--text" v-for="tag in tags" :key="tag" color="#08d3bc">
			{{ tag }}
		</v-chip>
    </div>
</template>

<script>
  export default {
		name: "PostTagBox",
    data() {
			return {
				tags: this.$attrs.tagList,
			}
		}
  }
</script>

<style>

</style>