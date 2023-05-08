<template>
    <form @submit="handleSubmit" class="add-form">
        <div class="form-control">
            <label>Množství</label>
            <input type="number" step="0.01" v-model="amount" name="amount"/>
        </div>
        <div class="form-control">
            <label>Měna</label>
            <select id="option" v-model="selectedOption">
                <option v-for="option in options" :value="option" :key="option">{{ option }}</option>
            </select>
        </div>  
        <input type="submit" value="Provést" class="btn btn-block" />

    </form>
</template>
<script>
import axios from 'axios'
export default{
    name: 'paymentForm',
    data(){
        return{
            amount: 0,
            selectedOption: '',
        };
    },
    props:{
        options:{
            type:Array,
            default:()=>[],
        }
    },
    methods: {
        handleSubmit(){
            const token = localStorage.getItem('jwt');
  
            const payment = {
            currencyAbbreviation: this.selectedOption,
            ammount: this.amount,
            }

            axios.post('http://localhost:8081/payment/withdraw', payment, { headers: { Authorization: `Bearer ${token}` } })
            .then(response => {
                alert(response.data);
            })
            .catch(error => {
                console.error(error);
            });
            this.$emit('form-submitted', this.selectedOption);
        }
    }
}
</script>
<style scoped>
select{
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>