<template>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody v-if="users && Object.keys(users).length !== 0">
            <tr v-for="user in users" v-bind:key="user.id">
                <td class="id">{{ user.id }}</td>
                <td class="login">{{ user.login }}</td>
                <td class="name">{{ user.name }}</td>
            </tr>
            </tbody>
            <tbody v-else>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

export default {
    name: "Users",
    props: ["users"]
}
</script>

<style scoped>

</style>