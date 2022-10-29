<#import "./common.ftlh" as c>

<@c.page>
    <h1>Users:</h1>

    <ul>
        <#list users as u>
            <li>
                <#if user?? && user.id == u.id>*</#if>
                <@c.userlink u/>
            </li>
        </#list>
    </ul>
</@c.page>
