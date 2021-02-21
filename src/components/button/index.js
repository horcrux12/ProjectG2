import React, { Component } from 'react'

class Button extends Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return ( 
          <button className={this.props.class} onClick={(this.props.funcClick) ? this.props.funcClick : ()=>{}}>{this.props.children}</button>
        );
    }
}
 
export default Button;