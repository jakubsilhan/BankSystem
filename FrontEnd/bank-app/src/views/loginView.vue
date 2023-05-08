<template>
    <div class="container">
      <title>Bank Login</title>
      <h1>Bank Login</h1>

      <loginForm @login-details="login"></loginForm>
    </div>
</template>

<script>
import loginForm from '@/components/loginForm.vue';
export default{
  name: 'loginView',
  components:{
    loginForm
  },
  methods: {
    login(){

    }
  }
}
</script>