<template>
    <form @submit="onSubmit" class="add-form">
      <div class="form-control">
        <label>E-mail</label>
        <input type="email" v-model="email" name="email" placeholder="john.doe@gmail.com" />
      </div>
      <div class="form-control">
        <label>Password</label>
        <input type="password" v-model="password" name="password" placeholder="password" />
      </div>  
      <input type="submit" value="Login" class="btn btn-block" />
    </form>
  </template>
  
  <script>
  export default {
    name: 'loginForm',
    data() {
      return {
        email: '',
        password: '',
      }
    },
    methods: {
      onSubmit() {
        console.log(this.email, this.password)
      },
    },
  }
  </script>
  
  <style scoped>
  .add-form {
    margin-bottom: 40px;
  }
  .form-control {
    margin: 20px 0;
  }
  .form-control label {
    display: block;
  }
  .form-control input {
    width: 100%;
    height: 40px;
    margin: 5px;
    padding: 3px 7px;
    font-size: 17px;
  }
  .form-control-check {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .form-control-check label {
    flex: 1;
  }
  .form-control-check input {
    flex: 2;
    height: 20px;
  }
  </style>