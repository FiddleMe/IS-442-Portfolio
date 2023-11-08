import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Slider from '@mui/material/Slider';

export default function DateRangeSlider({ minYear, maxYear, value, onChange }) {
    const handleChange = (event, newValue) => {
        onChange(newValue);
    };

    return (
        <Box >
            <Typography id="input-slider" gutterBottom>
                Please Select a Date Range
            </Typography>
            <Slider
                value={value}
                onChange={handleChange}
                defaultValue={10}
                valueLabelDisplay="auto"
                step={1}
                marks
                min={minYear} // Minimum year
                max={maxYear} // Maximum year
            />
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <span>{value[0]}</span>
                <span>{value[1]}</span>
            </div>
        </Box>
    );
}