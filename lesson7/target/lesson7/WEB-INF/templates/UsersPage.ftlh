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
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
        <template>
            <tr>
                <td class="user-id"></td>
                <td class="user-login"></td>
                <td class="user-creationTime"></td>
            </tr>
        </template>
    </div>

    <script>
        $(function () {
            const $users = $(".users");
            $users.find(".noData td").text("Loading...");

            $.ajax({
                method: "POST",
                data: { action: "findAll" },
                dataType: "json",
                success: function (response) {
                    $users.find(".noData").remove();
                    response["users"].forEach(function (user) {
                        const $tpl = $($users.find("template").prop("content")).clone();
                        $tpl.find("tr").attr("data-userId", user.id);
                        $tpl.find(".user-id").text(user.id);
                        $tpl.find(".user-login").text(user.login);
                        $tpl.find(".user-creationTime").text(user.creationTime);
                        $users.find("tbody").append($tpl);
                    });

                    $users.find("tbody tr").click(function () {
                        const userId = $(this).attr("data-userId");

                        $.ajax({
                            method: "POST",
                            data: { action: "find", userId },
                            dataType: "json",
                            success: function (response) {

                                notify("Creation time is " + response["user"].creationTime);

                                return false;
                            }
                        });


                        return false;
                    });

                    return false;
                }
            });

        });
    </script>
</@c.page>
