import React from 'react';
import { connect } from 'react-redux';
import { pushState } from 'react-router';

export function requireAuthentication(Component) {

    class AuthComponent extends React.Component {

        componentWillMount() {
            /*if (!this.props.isAuthenticated) {
                // redirect to login and add next param so we can redirect again after login
                this.props.dispatch(pushState(null, `/login?next=${this.props.location.pathname}`));
            }*/
            console.log('componentWillMount');
            console.log(this);
            if(!this.props.user.user.loginStatus) {
                this.props.history.push("/");
            }
        }

        render() {
            // render the component that requires auth (passed to this wrapper)
            return (
                <Component  {...this.props} />
            )
        }
    }

    const mapStateToProps =
        (state) => ({
            user : state.user,
        });

    return connect(mapStateToProps)(AuthComponent);

}