import { useCallback, useState } from 'react';
import store from '../stores/RobotStore';
import { robotAttributes, robotInitialState } from '../utils/constants';

export default function RobotForm() {
  const [robot, setRobot] = useState(robotInitialState);

  const setValue = useCallback(
    (value) => (event) => {
      setRobot({ ...robot, [value]: event.target.value });
    },
    [robot]
  );

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      store.addRobot({...robot, mass: parseFloat(robot.mass) > 500 ? parseFloat(robot.mass) : ""});
      setRobot(robotInitialState);
    },
    [robot]
  );

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="type">Robot Type</label>
      <input
        id="type"
        type="text"
        value={robot.type}
        onChange={setValue(robotAttributes.type)}
        placeholder='type'
      />
      <label htmlFor="name">Robot Name</label>
      <input
        id="name"
        type="text"
        value={robot.name}
        onChange={setValue(robotAttributes.name)}
        placeholder='name'
      />
      <label htmlFor="mass">Robot Mass</label>
      <input
        id="mass"
        type="number"
        value={robot.mass}
        onChange={setValue(robotAttributes.mass)}
        placeholder='mass'
      />
      <button type="submit">add</button>
    </form>
  );
}
