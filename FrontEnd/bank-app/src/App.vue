<template>
  <router-view></router-view>
  <pageFooter></pageFooter>
</template>

<script>
import pageFooter from './components/pageFooter.vue'
import  axios from 'axios';
console.log("URL",process.env.URL) 
console.log("MOREEE")
export default {
  name: 'App',
  methods: {
    getToken() {
      console.log(localStorage.getItem("jwt"))
      return localStorage.getItem("jwt");

    },
    // endpoint ve formátu /api/blablba
    async authApiCall(endpoint = "", body={}) {
      const response = await axios.post(`${process.env.VUE_APP_API_URL}${endpoint}`, body, { headers: { Authorization: `Bearer ${this.getToken()}` } })
      return response.data;
    },
  },

  components: {
    pageFooter
  }
}
</script>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
body {
  font-family: 'Poppins', sans-serif;
}
.container {
  max-width: 500px;
  margin: 30px auto;
  overflow: auto;
  min-height: 300px;
  border: 1px solid steelblue;
  padding: 30px;
  border-radius: 5px;
}
.btn {
  display: inline-block;
  background: #000;
  color: #fff;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  font-size: 15px;
  font-family: inherit;
}
.btn:focus {
  outline: none;
}
.btn:active {
  transform: scale(0.98);
}
.btn-block {
  display: block;
  width: 100%;
}
</style>
