import React from 'react';
import {Switch, Route} from 'react-router-dom';
import {connect} from 'react-redux';
import {getProfileFetch} from '../redux/actions';
import SignUp from '../components/SignUp';
import SignIn from '../components/SignIn';

class App extends React.Component {
    componentDidMount = () => {
        this.props.getProfileFetch()
    };

    render() {
        return (
            <div>
                <Switch>
                    <Route path="/signup" component={SignUp}/>
                    <Route path="/login" component={SignIn}/>
                </Switch>
            </div>
        );
    }
}

const mapDispatchToProps = dispatch => (
    {
        getProfileFetch: () => dispatch(getProfileFetch())
    }
);

export default connect(null, mapDispatchToProps)(App);
