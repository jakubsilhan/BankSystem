<template>
    <div class = "infoBox">
        <p> Jméno: {{ user.username }}<br>
            Email: {{ user.email }} <br>
            Číslo účtu: {{ user.accountNumber }} <br>
            Datum aktualizace kurzů: {{ currencyDate }} <br>
        </p>
    </div>
</template>

<script>
export default{
    name: "userInfo",
    props :{
        user: Object,
        currencyDate: String

    }
}
</script>

<style scoped>

.infoBox{
    border: 1px solid steelblue;
    padding: 20px;
    border-radius: 5px;
    flex-grow: 1;
}

</style>