<template>
  <div class="mainColumn">
    <header>
      <h1>Bankovní aplikace</h1>
    </header>
    <div class="headerRow">
      <div class="buttonColumn">
        <basicButton class="paymentButtons" text="Platba" @click="showPaymentForm = true"></basicButton>
        <basicButton class="paymentButtons" text="Vklad" @click="showDepositForm = true"></basicButton>
      </div>
      <userInfo :user="user" :currencyDate="currencyDate"></userInfo>
    </div>
    <balanceDisplay :balances="balances"></balanceDisplay>
    <MovementList :movements="reversedMovements" />
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
import PaymentForm from '@/components/paymentForm.vue';
import DepositForm from '@/components/depositForm.vue';
import router from '@/router';
export default {
  name: 'acountView',
  components: {
    basicButton,
    MovementList,
    userInfo,
    balanceDisplay,
    PaymentForm,
    DepositForm
  },


  data() {
    return {
      user: null,
      movements: [],
      balances: [],
      currencies: ["CZK", "AUD", "BRL", "BGN", "CNY", "DKK", "EUR", "PHP", "HKD", "INR", "IDR", "ISK", "ILS", "JPY", "ZAR", "CAD", "KRW", "HUF", "MYR", "MXN", "XDR", "NOK", "NZD", "PLN", "RON", "SGD", "SEK", "CHF", "THB", "TRY", "USD", "GBP"],
      currencyDate: "",
      showPaymentForm: false,
      showDepositForm: false,
    }
  },
  computed: {
    reversedMovements() {
      return this.movements.slice().reverse()
    }
  },
  async created() {
    const token = this.$root.getToken();

    if (!token) {
      router.push('/')
    }

    this.currencyDate = await this.$root.authApiCall("/loading/rate");
    this.balances = await this.$root.authApiCall("/loading/balances");
    this.movements = await this.$root.authApiCall("/loading/movements");
    this.user = await this.$root.authApiCall("/loading/user");

  },
  async refresh() {
    // Load balances
    this.balances = await this.$root.authApiCall("/loading/balances");
    // Load movements
    this.movements = await this.$root.authApiCall("/loading/movements");
    // Load user info
    this.user = await this.$root.authApiCall("/loading/user");
    // Refresh currency date
    this.currencyDate = await this.$root.authApiCall("/loading/rate");
    this.showDepositForm = false
    this.showPaymentForm = false
  }
}
</script>

<style scoped>
.mainColumn {
  display: flex;
  flex-direction: column;
  margin-left: 5px;
  margin-right: 5px;
}

.headerRow {
  display: flex;
  flex-direction: row;
}

.buttonColumn {
  display: flex;
  flex-direction: column;
}

.paymentButtons {
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