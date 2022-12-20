export default {
    users: {
        1: {
            id: 1,
            login: "mike",
            name: "Mike Mirzayanov"
        },
        10: {
            id: 10,
            login: "pashka",
            name: "Pavel Mavrin"
        },
        20: {
            id: 20,
            login: "artemka123",
            name: "Artem Treschyov"
        },
    },

    userId: null,

    posts: {
        1: {
            id: 1,
            title: "Post 1",
            text: "I'm post 1",
            userId: 10
        },
        2: {
            id: 2,
            title: "Post 2",
            text: "I'm post 2",
            userId: 1
        },
        10: {
            id: 10,
            title: "Post 10",
            text: "I'm post 10",
            userId: 10
        },
        30: {
            id: 30,
            title: "Hello",
            text: "Hello, world!",
            userId: 20
        }
    }
}
