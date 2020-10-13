export const userPostFetch = singUpRequest => {
    //todo что такое dispatch
    return dispatch => {
        return fetch("http://localhost:8075/signup",
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                    Accept: 'application/json',
                },
                body: JSON.stringify({singUpRequest})
            }
        )
        //todo не выкупаю, что идет дальше
            .then(resp => resp.json())
            .then(data => {
                    if (data.message) {
                        //Тут прописываем логику
                    } else {
                        localStorage.setItem("jwt", data.jwt);
                        dispatch(loginUser(data.singUpRequest))
                    }
                }
            )
    }
};

const loginUser = userObj => (
    {
        type: 'LOGIN_USER',
        payload: userObj
    }
);

export const userLoginFetch = singInRequest => {
    return dispatch => {
        //todo посмотреть че к чему
        return fetch("http://localhost:3000/api/v1/login", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                    Accept: 'application/json',
                },
                body: JSON.stringify({singInRequest})
            }
        )
            .then(resp => resp.json())
            .then(data => {
                    if (data.message) {
                        //тут ваша логика
                    } else {
                        localStorage.setItem("jwt", data.jwt);
                        dispatch(loginUser(data.singInRequest))
                    }
                }
            )
    }
};

export const getProfileFetch = () => {
    return dispatch => {
        //todo прочекать
        const jwt = localStorage.jwt;
        if (jwt) {
            return fetch("http://localhost:3000/api/v1/profile", {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json',
                    Accept: 'application/json',
                    'Authorization': `Bearer ${jwt}`
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        // Будет ошибка если token не дествительный
                        localStorage.removeItem("token")
                    } else {
                        dispatch(loginUser(data.user))
                    }
                })
        }
    }
};