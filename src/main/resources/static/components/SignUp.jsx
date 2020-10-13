import React from 'react';
import {connect} from 'react-redux';
import {userPostFetch} from '../redux/actions';

class SignUp extends React.Component {

    state = {
        email: "",
        name: "",
        password: "",
    };

    //todo не очень выкупаю. Что такое ивент и таргет?
    handleChange = event => {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    };

    handleSubmit = event => {
        event.preventDefault();
        //todo не выкупаю
        this.props.userPostFetch(this.state)
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <h1>Sign Up For An Account</h1>

                <label>Email</label>
                <input
                    name='email'
                    placeholder='Email'
                    value={this.state.email}
                    onChange={this.handleChange}
                />

                <label>Username</label>
                <input
                    name='name'
                    placeholder='Username'
                    value={this.state.username}
                    onChange={this.handleChange}
                />
                <br/>

                <label>Password</label>
                <input
                    type='password'
                    name='password'
                    placeholder='Password'
                    value={this.state.password}
                    onChange={this.handleChange}
                /><br/>

                <input type='submit'/>
            </form>
        )
    }
}

const mapDispatchToProps = dispatch => (
    {
        userPostFetch: userInfo => dispatch(userPostFetch(userInfo))
    }
);

export default connect(null, mapDispatchToProps)(SignUp);