<template>
  <div class = "mainColumn">
    <header>
      <h1>Bankovní aplikace</h1>
    </header>
    <div class = "headerRow">
      <div class = "buttonColumn">
        <basicButton class = "paymentButtons" text = "Platba" @click="showPaymentForm=true"></basicButton>
        <basicButton class = "paymentButtons" text = "Vklad" @click="showDepositForm=true"></basicButton>
      </div>
      <userInfo 
        :user="user"
      ></userInfo>
    </div>
    <balanceDisplay :balances = "balances"></balanceDisplay>
    <MovementList :movements = "reversedMovements"/>
    <div v-if="showPaymentForm" class="popup">
      <PaymentForm :options="currencies" @form-submitted="refresh"></PaymentForm>
    </div>
    <div v-if="showDepositForm" class="popup">
      <DepositForm :options="currencies" @form-submitted="refresh"></DepositForm>
    </div>
    <div v-if="showDepositForm">
    
    </div>
  </div>
</template>

<script>
import basicButton from '@/components/basicButton.vue';
import MovementList from '@/components/movementList.vue';
import userInfo from '@/components/userInfo.vue';
import balanceDisplay from '@/components/balanceDisplay.vue';
import axios from 'axios'
import PaymentForm from '@/components/paymentForm.vue';
import DepositForm from '@/components/depositForm.vue';
import router from '@/router';
export default{
  name: 'acountView',
  components: {
    basicButton,
    MovementList,
    userInfo,
    balanceDisplay,
    PaymentForm,
    DepositForm
},
  data(){
    return{
      user: null,
      movements: [],
      balances: [],
      currencies: ["CZK","AUD","BRL","BGN","CNY","DKK","EUR","PHP","HKD","INR","IDR","ISK","ILS","JPY","ZAR","CAD","KRW","HUF","MYR","MXN","XDR","NOK","NZD","PLN","RON","SGD","SEK","CHF","THB","TRY","USD","GBP"],
      showPaymentForm: false,
      showDepositForm: false,
    }
  },
  computed:{
    reversedMovements() {
        return this.movements.slice().reverse()
    }
  },
  created() {
    const token = localStorage.getItem('jwt');

    if (!token) {
    router.push('/')
  }

    // Load balances
    axios.post('http://localhost:8081/loading/balances', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
      this.balances = response.data;
    })
    .catch(error => {
      console.error(error);
    });
    // Load movements
    axios.post('http://localhost:8081/loading/movements', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
        this.movements = response.data;
      })
      .catch(error => {
        console.error(error);
    });
    // Load user info
    axios.post('http://localhost:8081/loading/user', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
        this.user = response.data;
      })
      .catch(error => {
        console.error(error);
    });
  },
  refresh(){
    const token = localStorage.getItem('jwt');
    // Load balances
    axios.post('http://localhost:8081/loading/balances', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
      this.balances = response.data;
    })
    .catch(error => {
      console.error(error);
    });
    // Load movements
    axios.post('http://localhost:8081/loading/movements', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
        this.movements = response.data;
      })
      .catch(error => {
        console.error(error);
    });
    // Load user info
    axios.post('http://localhost:8081/loading/user', {}, { headers: { Authorization: `Bearer ${token}` } })
      .then(response => {
        this.user = response.data;
      })
      .catch(error => {
        console.error(error);
    });
    this.showDepositForm=false
    this.showPaymentForm=false
  }
}
</script>

<style scoped>
.mainColumn{
  display: flex;
  flex-direction: column;
  margin-left: 5px;
  margin-right: 5px;
}

.headerRow{
  display: flex;
  flex-direction: row;
}

.buttonColumn{
  display: flex;
  flex-direction: column;
}

.paymentButtons{
    width: 200px;
    height: 50px;
}
.popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 9999;
  background-color: white;
  padding: 20px;
  border: 1px solid black;
}
</style>