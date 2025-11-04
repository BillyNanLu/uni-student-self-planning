<script setup>
import { ref, computed } from 'vue';
import useUserInfoStore from '@/stores/userInfo';
import { useTokenStore } from '@/stores/token';

import Banner from '../components/home/Banner.vue';
import FeaturePreview from '../components/home/FeaturePreview.vue';
import CaseStats from '../components/home/CaseStats.vue';
import LoginFloat from '../components/home/LoginFloat.vue';
import EvaluateTip from '../components/home/EvaluateTip.vue';
import PersonalPlanCard from '../components/home/PersonalPlanCard.vue';

const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();

// 计算登录状态
const isLogin = computed(() => !!tokenStore.token);
</script>

<template>
  <div class="home-page">
    <!-- Banner区 -->
    <Banner />
    <!-- 功能预览区 -->
    <FeaturePreview />

    <!-- 登录后模块 -->
    <EvaluateTip v-if="isLogin" />
<!--    <PersonalPlanCard v-if="isLogin && userInfoStore.info.planInfo" />-->
    <PersonalPlanCard v-if="isLogin" />

    <!-- 案例数据区 -->
    <CaseStats />


    <!-- 悬浮登录入口（仅未登录显示） -->
    <LoginFloat v-if="!isLogin" />
  </div>
</template>

<style scoped>

</style>