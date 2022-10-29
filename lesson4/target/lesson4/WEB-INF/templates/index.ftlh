<#import "./common.ftlh" as c>

<@c.page>
    <h1>Users:</h1>

    <ul>
        <#list users as u>
            <li><a href="/user?handle=${u.handle}">${u.name}</a></li>
        </#list>
    </ul>
</@c.page>
