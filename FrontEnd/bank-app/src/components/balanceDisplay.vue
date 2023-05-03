<template>
 <div class="display">
    <h3>Balances:</h3>
    <div :key="balance.id" v-for="balance in balances">
      <balanceItem :balance="balance"/>
    </div>
 </div>
</template>
<script>
import balanceItem from './balanceItem.vue';
export default{
    name : "balanceDisplay",
    components: {
        balanceItem,
    },
    props: {
        balances: Array
    }
}
</script>

<style scoped>

.display{
    border: 1px solid steelblue;
    border-radius: 5px;
    margin-top: 2px;
    padding-left: 5px;
}
h3{
    text-decoration: underline;
}
</style>