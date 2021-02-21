import React, { Component } from 'react';

class FormField extends Component {
    // constructor(props) {
    //     super(props);
    //     this.state = {  }
    // }
    render() { 
        return ( 
            <div className={this.props.classes}>
                {this.props.children}
            </div>
        );
    }
}
 
export default FormField;