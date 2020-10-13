import React from 'react';
import {userLoginFetch} from '../redux/actions';
import connect from "react-redux/es/connect/connect";

class SignIn extends React.Component {

    state = {
        name: "",
        password: ""
    };

    handleChange = event => {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    };

    handleSubmit = event => {
        event.preventDefault();
        this.props.userLoginFetch(this.state)
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <h1>Please login</h1>

                <lable>Name:</lable>
                <input
                    name="name"
                    placeholder="Name"
                    value={this.state.name}
                    onChange={this.handleChange}
                />

                <br/>

                <label>Password:</label>
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={this.state.password}
                    onChange={this.state.password}
                />

                <br/>

                <input type='submit'/>
            </form>
        );
    }
}

const mapDispatchToProps = dispatch => (
    {
        userLoginFetch: userInfo => dispatch(userLoginFetch(userInfo))
    }
);

export default connect(null, mapDispatchToProps)(SignIn);