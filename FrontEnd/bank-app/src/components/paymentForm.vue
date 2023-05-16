<template>
    <h1>Platba</h1>
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
        async handleSubmit(){
  
            const payment = {
            currencyAbbreviation: this.selectedOption,
            ammount: this.amount,
            }
            const data = await this.$root.authApiCall("/payment/withdraw",payment);
            alert(data)
            this.$emit('form-submitted', this.selectedOption);
        }
    }
}
</script>
<style scoped>
.form-control label {
    display: block;
}
input{
    margin-left: 1px;
}
select{
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>