<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u=c.findBy(users, "handle", handle!)!/>

    <#if u?has_content>
        <h1>User</h1>
        <h4>Name: ${u.name}</h4>
        <h4>Handle: ${u.handle}</h4>
        <h4>Id: ${u.id}</h4>
    <#else>
        No such user
    </#if>


</@c.page>
