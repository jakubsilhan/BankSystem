<template>
    <div class = "balance">
        <h3>{{ balance.currency }}</h3>
        <p>
            {{ balance.amount }} {{ balance.abbreviation }}
        </p>
    </div>
</template>

<script>
export default{
    name: "balanceItem",
    props:{
        balance: Object
    }
}
</script>

<style scoped>
  .balance{
    border-bottom: 1px dashed;
  }
</style>