<template>
    <div class="movement">
      <h3>
        {{ movement.desc }}
      </h3>
      <p>{{ movement.amount }}</p>
      <p>{{ movement.day }}</p>
    </div>
  </template>
  
  <script>
  export default {
    name: 'movementInfo',
    props: {
      movement: Object,
    },
  }
  </script>
  
<style scope>
  .movement {
    border-bottom: 1px dashed;
  }
</style>