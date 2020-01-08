import React from 'react';

import './app.scss';

export const App = () => {
    const [activeWord, setActiveWord] = React.useState(0);
    const [isAlternate, setAlternate] = React.useState(false);
    const [bump, setBump] = React.useState(false);
    const [transition, setTransition] = React.useState(false);
    const [bumpOver, setBumpOver] = React.useState(false);

    React.useEffect(() => {
        let triggerTime = 800;
        if (activeWord === 0) triggerTime = 1200;
        if (bump) triggerTime = 500;
        if (isAlternate) triggerTime = 900;

        window.setTimeout(() => {
            if (activeWord < 3) setActiveWord(activeWord + 1);
            else if (!bump) setBump(true);
            else if (!isAlternate) {
                setAlternate(true);
                setBumpOver(true);
            } else {
                setTransition(true);
                setBumpOver(false);
            }
        }, triggerTime);
    }, [activeWord, bump, isAlternate]);

    return (
        <div className={`app-container ${transition ? 'transition' : ''}`}>
            <div className={`alternate-animation ${isAlternate ? 'grow' : ''}`} />
            <div className="message">
                <div className={`message-constant ${isAlternate ? 'alternate' : ''}`}>
                    It's
                </div>
                <div className={`bump-border ${bumpOver ? 'grow-and-shrink' : ''}`} />
                <div className={`message-variable ${isAlternate ? 'alternate' : ''}`}>
                    <div className={`${bump ? 'bump' : ''}`} style={{transform: `translateY(-${activeWord * 150}px)`}}>
                        <div>Simple</div>
                        <div>Versatile</div>
                        <div>Innovative</div>
                        <div>Hera</div>
                    </div>
                </div>
            </div>
        </div>
    );
}
