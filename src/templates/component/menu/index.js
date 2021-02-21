import React, { Component } from 'react';
import { connect } from "react-redux";

import Action from "../../../action";

class Menu extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }

    logout = () => {
        this.props.doLogout()
    }

    render() { 
        return (  
            <div className="menu">
                <button onClick={this.logout} className="icon-logout">
                    logout
                </button>
            </div>
        );
    }
}

const mapDispatchToProps = dispatch => {
    return {
        doLogout : () => dispatch({type: Action.LOGOUT})
    }
}

export default connect(null, mapDispatchToProps)(Menu);