<template>
  <div class="movementList">
    <h3>Movements:</h3>
    <div :key="movement.id" v-for="movement in movements">
      <movementInfo :movement="movement"/>
    </div>
  </div>
  </template>

<script>
import movementInfo from './movementInfo.vue';
export default{
  name: 'movementList',
  components: {
    movementInfo,
  },
  props: {
    movements: Array,
  }
}
</script>
<style scoped>
  .movementList {
    border: 1px solid steelblue;
    border-radius: 5px;
    margin-top: 2px;
    padding-left: 5px;
  }

  h3{
    text-decoration: underline;
  }

</style>