<template>
  <div id="app">
    <div class="container-fluid">
      <div class="row">
        <div class="col-12">
          <top/>
        </div>
      </div><!--top row end-->
      <div class="row justify-content-center" style="margin-top:35px;">
        <div class="col-12">
          <router-view/>
        </div>
      </div>
    </div>
    <div style="position:absolute;bottom:0px;width:100%;">
      <bottom/>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App'
}
</script>

<style>

</style>
