<template>
 <div class="page-edit-team">
        <div class="columns is-multiline">
            <div class="column is-12">
                <h1 class="title">Edit Team</h1>
                <div class="column is-12">
                    <div class="field">
                           <label>Name</label>
                           <div class="control">
                               <input type="text" class="input" v-model="team.name">
                           </div>
                    </div>
                    <div class="field">
                           <label>Organisation Number</label>
                           <div class="control">
                               <input type="text" class="input" v-model="team.org_number">
                           </div>
                    </div>
                    <div class="field">
                           <label>Invoice Number</label>
                           <div class="control">
                               <input type="number" class="input" v-model="team.first_invoice_number">
                           </div>
                    </div>
                    <div class="field">
                           <label>Bank Account </label>
                           <div class="control">
                               <input type="text" class="input" v-model="team.bank_account">
                           </div>
                    </div>
                    <div class="field">
                           
                           <div class="control">
                               <button class="button is-success" @click="submitForm">Save</button>    
                           </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios'
import {toast} from 'bulma-toast'

export default{
    name : 'EditTeam',
    data(){
        return{
            team:{}
        }
    },
    async mounted(){
        await this.getOrCreateTeam();
    },
    methods:{
        getOrCreateTeam(){
            axios.get('/api/v1/teams')
            .then(response=>{
                this.team = response.data[0]
            })
            .catch(error=>{
                console.log(JSON.stringify(error))
            })
        },
        submitForm(){
           axios.patch(`api/v1/teams/${this.team.id}/`, this.team)
           .then(response=>{
            toast({
                message: 'Changes Saved Successfully',
                type:'is-success',
                dismissible:true,
                pauseOnHover:true,
                duration:2000,
                position: 'top-right',
            })

            this.$router.push('/dashboard/my-account')
           }).catch(error=>{
                console.log(JSON.stringify(error))
            }) 
        }
    }
}
</script>