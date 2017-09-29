import React from 'react'

const options = [
    '1080x720',
    '1280x720',
    '1440x900',
    '1600x900',
    '1920x1080',
    '2560x1600'
];

class DropDown extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selected: options[0]
        };
        this._onSelect = this._onSelect.bind(this)
    }

    _onSelect(option) {
        this.setState({selected: option})
    }

    render() {
        const defaultOption = this.state.selected;

        return (
            <section>
                <Dropdown options={options} onChange={this._onSelect} value={defaultOption} placeholder="Select an option" />
            </section>
        )
    }
}

export default DropDown