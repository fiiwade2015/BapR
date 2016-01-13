import { UPDATE_MENU } from 'constants/menu';

export default {
  changeMenu: (menuItem, locationId) => {
  	return { 
  		type : UPDATE_MENU, 
  		payload: { 
  			displayName: menuItem, 
  			locationId: locationId
  		}
  	}
  }
};