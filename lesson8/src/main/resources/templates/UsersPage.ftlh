<#-- @ftlvariable name="users" type="ru.itmo.wp.lesson8.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
                <#list users as user>
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.login}</td>
                        <td>${user.creationTime}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>


</@c.page>
