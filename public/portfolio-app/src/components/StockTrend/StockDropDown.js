import React from 'react';
import { DropdownButton, Dropdown } from 'react-bootstrap';

function StockDropdown({ stockIds, setSelectedStock }) {
    return (
        <DropdownButton id="stockDropdown" title="Select Stock">
            {Array.from(stockIds).map((stockId) => (
                <Dropdown.Item
                    key={stockId}
                    onClick={() => setSelectedStock(stockId)}
                >
                    {stockId}
                </Dropdown.Item>
            ))}
        </DropdownButton>
    );
}

export default StockDropdown;
