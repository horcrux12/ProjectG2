import React, { Component } from 'react'

class Input extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return (  
            <input type={this.props.type} defaultValue={this.props.value}/>
        );
    }
}
 
export default Input;