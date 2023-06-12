import { error } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export function load({ params }) {
    if (params.menuId) {
        return {
            menuId: params.menuId,
        };
    }

    throw error(404, 'Not found');
}