<template>
    <div class = "infoBox">
        <p> jméno: {{ user.username }}<br>
            email: {{ user.email }} <br>
            číslo účtu: {{ user.accountNumber }}
        </p>
    </div>
</template>

<script>
export default{
    name: "userInfo",
    props :{
        user: Object

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