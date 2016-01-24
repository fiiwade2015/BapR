import { UPDATE_MENU, UPDATE_PLAN_VIEW } from 'constants/menu';

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
  }
};