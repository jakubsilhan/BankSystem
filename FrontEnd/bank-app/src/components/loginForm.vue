<template>
  <div>
    <div v-if="!verified">
      <form @submit="submitUserDetails" class="add-form">
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
    </div>
    <div v-else>
      <form @submit="submitVerificationCode" class="add-form">
        <div class="form-control">
          <label>Verification code</label>
          <input type="text" v-model="verificationCode" required />
        </div>
          <input type="submit" value="Verify" class="btn btt-block"/>
      </form>
    </div>
  </div>
</template>
  
  <script>
  import axios from 'axios'
  export default {
    name: 'loginForm',
    data() {
      return {
        email: '',
        password: '',
        verificationCode: '',
        verified: false,
      }
    },
    methods: {
      async submitUserDetails() {
        event.preventDefault();
        try{
              // send user details to backend for validation
          const response = await axios.post('http://localhost:8081/authentication/check', {
          email: this.email,
          password: this.password
          })
          if (response.data == true) {
            // user details are valid, show verification code form
            this.verified = true
          } else if (response.data==false) {
            // user details are invalid, display error message
            alert('Invalid email or password')
            console.error('Invalid user details')
          }
        }catch(error){
          console.error(error);
          alert('Error occured while processing your request.')
        }
      },
      async submitVerificationCode(){
        event.preventDefault();
        const response = await axios.post('http://localhost:8081/authentication/validate', {
          email: this.email,
          code: this.verificationCode
        })
        if (response.data != "Invalid code") {
          // verification code is valid, get JWT token
          //const data = await response.json()
          const data = response.data
          localStorage.setItem('jwt', data)
          // redirect to account view
          this.$router.push('/account')
        } else {
          // verification code is invalid, display error message
          alert('Invalid verification code')
          console.error('Invalid verification code')
        }
      }
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