import { createRouter, createWebHistory } from 'vue-router'
import loginView from '../views/loginView'
import acountView from '../views/acountView'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: loginView,
  },
  {
    path: '/account',
    name: 'Account',
    component: acountView,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router