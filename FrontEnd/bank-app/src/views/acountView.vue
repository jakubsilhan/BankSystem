<template>
  <div class = "mainColumn">
    <header>
      <h1>Bankovní aplikace</h1>
    </header>
    <div class = "headerRow">
      <div class = "buttonColumn">
        <basicButton class = "paymentButtons" text = "Platba"></basicButton>
        <basicButton class = "paymentButtons" text = "Vklad"></basicButton>
      </div>
      <userInfo 
        :user="user"
      ></userInfo>
    </div>
    <balanceDisplay :balances = "balances"></balanceDisplay>
    <MovementList :movements = "movements"/>
  </div>
</template>

<script>
import basicButton from '@/components/basicButton.vue';
import MovementList from '@/components/movementList.vue';
import userInfo from '@/components/userInfo.vue';
import balanceDisplay from '@/components/balanceDisplay.vue';
export default{
  name: 'acountView',
  components: {
    basicButton,
    MovementList,
    userInfo,
    balanceDisplay
  },
  data(){
    return{
      Owner: Object,
      movements: [],
      balances: []
    }
  },
  created() {
    this.movements = [
      {
        id: 1,
        desc: 'Pekarna',
        amount: '124 CZK',
        day: '23.4.2023'
      },
      {
        id: 2,
        desc: 'Elektro',
        amount: '1085 CZK',
        day: '24.2.2023'
      }
    ]
    this.balances = [
      {
        id: 1,
        currency: 'Česká Koruna',
        abbreviation: "CZK",
        amount: 1234
      },
      {
        id: 2,
        currency: 'Euro',
        abbreviation: "EUR",
        amount: 352
      }
    ]
    this.user = {
      id:1,
      name: "Jakub Silhan",
      email: "john.doe@gmail.com",
      password: "12345",
      accountNumber: 1
    }
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
</style>