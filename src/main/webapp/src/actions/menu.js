import { UPDATE_MENU, UPDATE_PLAN_VIEW,UPDATE_DISPLAY_VIEW } from 'constants/menu';

export default {
  changeMenu: (menuItem, locationId) => {
  	return { 
  		type : UPDATE_MENU, 
  		payload: { 
  			displayName: menuItem, 
  			locationId: locationId
  		}
  	}
  },
  updatePlanView: (planId,viewStatus) => {
  	return {
  		type : UPDATE_PLAN_VIEW,
  		payload: {planId: planId, status: viewStatus}
  	}
  },
  updateDisplayView: (locationId, data) => {
    return {
      type: UPDATE_DISPLAY_VIEW,
      payload: {locationId : locationId, displayedData: data}
    }
  }
};